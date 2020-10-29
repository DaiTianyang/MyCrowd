package com.atguigu.crowd.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.mvc.constant.CrowdConstant;
import com.atguigu.crowd.service.MemberService;
import com.atguigu.crowd.util.ResultEntity;

@RestController
public class MemberProviderHandler {

	@Autowired
	private MemberService memberService;
	
	/**
	 * 添加新用户
	 * @param memberPO
	 * @return
	 */
	@RequestMapping("/save/member/remote")
	public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO){
		try {
			memberService.saveMember(memberPO);
			
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			
			if(e instanceof DuplicateKeyException){
				
				return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
			return ResultEntity.failed(e.getMessage());
		}
	}
	/**
	 * 根据传入用户账号查询出对应的MemberPO对象
	 * @param loginAcct
	 * @return
	 */
	@RequestMapping("/get/memberpo/by/login/acct/remote")
	public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginAcct") String loginAcct) {

		try {
			//1.调用本地service进行查询 如果不捕获到异常 下面返回异常消息
			MemberPO memberPO = memberService.getMemberPOByLoginAcctRemote(loginAcct);
			return ResultEntity.successWithData(memberPO);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}

	}

}
