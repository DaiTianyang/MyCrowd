package com.atguigu.crowd.handler;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.api.RedisRemoteService;
import com.atguigu.crowd.config.ShortMessageProperties;
import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.vo.MemberLoginVO;
import com.atguigu.crowd.entity.vo.MemberVO;
import com.atguigu.crowd.mvc.constant.CrowdConstant;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import com.google.common.base.Objects;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Controller
public class MemberHandler {

	@Autowired
	private ShortMessageProperties messageProperties;

	@Autowired
	private RedisRemoteService redisRemoteService;

	@Autowired
	private MySQLRemoteService mySQLRemoteService;

	private Logger logger = LoggerFactory.getLogger(MemberHandler.class);

	/**
	 * 退出登录，清空session域中的值
	 * @param session
	 * @return
	 */
	@RequestMapping("/auth/member/do/logout")
	public String doLoginOut(HttpSession session){
		session.invalidate();
		return "redirect:http://www.crowd.com/";
	}
	/**
	 * 用户登录，登录成功将memberloginvo存入session域中
	 * @param loginacct
	 * @param userpswd
	 * @param modelMap
	 * @param session
	 * @return
	 */
	@RequestMapping("/auth/member/do/login")
	public String login(
			@RequestParam("loginacct") String loginacct, 
			@RequestParam("userpswd") String userpswd,
			ModelMap modelMap, 
			HttpSession session) {
		
		if(loginacct == "" || userpswd == "" || loginacct.length() == 0 || userpswd.length() == 0){
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_NOACCT_NO_PAWD);
			return "member-login";
		}
		// 调用远程接口来查询出MemberPO
		ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
		// 判断获取是否成功，如果失败返回错误消息
		if (ResultEntity.FAILED.equals(resultEntity.getOperationResult())) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getOperationMessage());
			return "member-login";
		}
		// 获取MemberPO
		MemberPO memberPO = resultEntity.getQueryData();
		if (memberPO == null) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
			return "member-login";
		}
		// 对表单提交的密码进行加密
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//String formPassword = passwordEncoder.encode(userpswd);
		// 从MemberPO中获取用户密码
		String databaseUserPswd = memberPO.getUserPswd();
		// 对加密后的密码与数据库密码进行比对，如果不一致则提示错误信息
		//必须是表单传来的未加密的密码在前面，加密后的密码在后面否则比对不成功会出现密码正确也登录不进去的异常
		boolean matcheResult = passwordEncoder.matches(userpswd,databaseUserPswd);
		if (!matcheResult) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
			return "member-login";
		}
		// 创建一个MemberLoginVO对象来存入session域
		MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getLoginAcct(), memberPO.getEmail(),
				memberPO.getUserName());
		session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);
		//localhost:80
		return "redirect:http://www.crowd.com/auth/member/to/center/page";
	}

	/**
	 * 用户注册，匹配验证码等
	 * 
	 * @param memberVO
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/auth/do/member/register")
	public String register(MemberVO memberVO, ModelMap modelMap) {
		// 1.获取用户输入的手机号
		String phone = memberVO.getPhone();

		// 2.拼接redis中存储验证码的key
		String key = CrowdConstant.REDIS_CODE_PREFIX + phone;

		// 3.从redis中读取对应key所对应的value
		ResultEntity<String> resultEntity = redisRemoteService.getRedisStringValueByKeyRemote(key);

		// 4.检查查询操作是否有效 满足success 并且查询结果不为null
		String result = resultEntity.getOperationResult();

		if (ResultEntity.FAILED.equals(result)) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getOperationResult());
			return "member-reg";
		}
		String rediscode = resultEntity.getQueryData();
		if (rediscode == null) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
			return "member-reg";
		}
		// 5.如果从redis能查询到验证码，比较redis取出的验证码和表单提交的验证码
		String code = memberVO.getCode();

		if (!Objects.equal(code, rediscode)) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
			return "member-reg";
		}
		// 6.如果比较一致 则从redis中删除验证码
		redisRemoteService.removeRedisKeyRemote(key);

		// 7.执行密码的加密
		BCryptPasswordEncoder encoderPassword = new BCryptPasswordEncoder();
		String userPswdBeForeEncode = memberVO.getUserPswd();
		String afterEncode = encoderPassword.encode(userPswdBeForeEncode);
		memberVO.setUserPswd(afterEncode);

		// 8.完成保存
		// 创建一个空的memberpo对象
		MemberPO memberPO = new MemberPO();

		// 复制属性调用spring提供的工具
		BeanUtils.copyProperties(memberVO, memberPO);

		// 调用远程的方法
		ResultEntity<String> saveMemberRemoteResultEntity = mySQLRemoteService.saveMemberRemote(memberPO);
		if (ResultEntity.FAILED.equals(saveMemberRemoteResultEntity.getOperationResult())) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveMemberRemoteResultEntity.getOperationMessage());
			return "member-reg";
		}
		// 使用重定向避免重复提交表单造成注册被执行多次
		return "redirect:/auth/member/to/login/page";
	}

	/**
	 * 发送验证码并存入redis String host = "https://smssend.shumaidata.com"; String path
	 * = "/sms/send"; String method = "POST"; String appcode =
	 * "e28d67887269409a851de4fb3c50d02a";
	 * 
	 * @param host
	 *            https://smssend.shumaidata.com
	 * @param path
	 *            /sms/send
	 * @param method
	 *            POST
	 * @param appcode
	 *            e28d67887269409a851de4fb3c50d02a
	 * @param phone
	 *            15645693659
	 * @param templatedId
	 *            "M09DD535F4"
	 * @return 成功：返回生成的验证码 失败：返回失败原因或异常结果
	 *         状态码：200正常，400URL无效，401AppCode错误，403次数用完啦，500API网管错误
	 */
	@ResponseBody
	@RequestMapping("/auth/member/send/short/message.json")
	public ResultEntity<String> sendMessage(@RequestParam("phone") String phone) {
		String host = messageProperties.getHost();
		String path = messageProperties.getPath();
		String method = messageProperties.getMethod();
		String appcode = messageProperties.getAppCode();
		String templatedId = messageProperties.getTemplatedId();

		/*
		 * String host = "https://smssend.shumaidata.com"; String path
		 * ="/sms/send"; String method = "POST"; String
		 * appcode="e28d67887269409a851de4fb3c50d02a"; String templatedId
		 * ="M09DD535F4";
		 */

		// logger.debug("============" + host + path + method + appcode +
		// templatedId);
		// 1.发送验证码
		ResultEntity<String> sendMessageResult = CrowdUtil.sendCodeByShortMessage(host, path, method, appcode, phone,
				templatedId);
		// 2.验证码是否发送成功
		if (ResultEntity.SUCCESS.equals(sendMessageResult.getOperationResult())) {
			// 3.发送成功将验证码存入Redis
			// 获取验证码
			String code = sendMessageResult.getQueryData();
			// 拼接一个用于再redis中存储验证码的key
			String key = CrowdConstant.REDIS_CODE_PREFIX + phone;
			// 将验证码存入redis并设置过期时间
			ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code,
					15, TimeUnit.MINUTES);
			// 判断结果是否成功
			if (ResultEntity.SUCCESS.equals(saveCodeResultEntity.getOperationResult())) {

				return ResultEntity.successWithoutData();

			} else {

				return saveCodeResultEntity;
			}
		} else {

			return sendMessageResult;
		}

	}

}
