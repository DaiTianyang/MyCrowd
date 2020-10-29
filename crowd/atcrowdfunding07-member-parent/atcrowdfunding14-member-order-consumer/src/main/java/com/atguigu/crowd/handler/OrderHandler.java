package com.atguigu.crowd.handler;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.entity.vo.MemberLoginVO;
import com.atguigu.crowd.entity.vo.OrderProjectVo;
import com.atguigu.crowd.mvc.constant.CrowdConstant;
import com.atguigu.crowd.util.ResultEntity;

@Controller
public class OrderHandler {

	@Autowired
	private MySQLRemoteService mySQLRemoteService;
	
	
	
	/**
	 * 调用远程接口把页面传来的表单数据进行保存到数据库
	 * 并从session域中取出returnCount
	 * 进行重定向 重新返回该页面进行页面数据的刷新
	 * 
	 * @param addressVO
	 * @param session
	 * @return
	 */
	@RequestMapping("/save/address")
	public String saveAddress(
			AddressVO addressVO,
			HttpSession session){
		
		//调用远程接口进行保存页面发来的AddressVO
		ResultEntity<String> resultEntity = mySQLRemoteService.saveAddressRemote(addressVO);
		
		//如果保存成功，则取出session域中的orderProjectVo
		OrderProjectVo orderProjectVo = (OrderProjectVo) session.getAttribute("orderProjectVo");
		
		//取出orderProjectVo对象中的returnCount
		Integer returnCount = orderProjectVo.getReturnCount();
		
		//进行重定向，刷新页面数据
		return "redirect:http://www.crowd.com/order/confirm/order/"+returnCount;
	}
	
	/**
	 * 将传过来的returnCount回报数量合并进session域中
	 * 再查询出当前登录用户的memberId
	 * 通过memberId再查询出对应的收货的详细地址并存入session域中 返回confirm_order页面
	 * 
	 * @param returnCount
	 * @param session
	 * @return
	 */
	@RequestMapping("/confirm/order/{returnCount}")
	public String showConfirmOrderInfo(
			@PathVariable("returnCount") Integer returnCount,
			HttpSession session){
		//1.把接收到的returnCount合并进session域中
		//从session域中取出OrderProjectVo对象
		OrderProjectVo orderProjectVo = (OrderProjectVo) session.getAttribute("orderProjectVo");
		//将获取到的returnCount添加进OrderProjectVo对象中
		orderProjectVo.setReturnCount(returnCount);
		//将OrderProjectVo重新添加进session域中
		session.setAttribute("orderProjectVo", orderProjectVo);
		
		//2.获取当前已经登录的用户id
		MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

		Integer memberId = memberLoginVO.getId();
		
		//3.查询现有的收获地址信息(通过memberId)
		ResultEntity<List<AddressVO>> resultEntity = mySQLRemoteService.getAddressVORemote(memberId);
		//判断取出收获地址是否成功
		if(ResultEntity.SUCCESS.equals(resultEntity.getOperationResult())){
			List<AddressVO> list = resultEntity.getQueryData();
			session.setAttribute("addressVOList", list);
		}
		return "confirm_order";
	}
	
	/**
	 * 通过点击支持按钮传入相关参数来跳转到confirm_return页面并显示对应的信息
	 * @param projectId
	 * @param returnId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/confirm/return/info/{projectId}/{returnId}")
	public String showReturnConfirmInfo(
			@PathVariable("projectId") Integer projectId,
			@PathVariable("returnId") Integer returnId,
			HttpSession session){
		//调用远程接口
		ResultEntity<OrderProjectVo> resultEntity = mySQLRemoteService.getOrderProjectVoRemote(projectId,returnId);
		//判断获取数据是否成功
		if(ResultEntity.SUCCESS.equals(resultEntity.getOperationResult())){
			//如果成功取出获取到的数据
			OrderProjectVo orderProjectVo = resultEntity.getQueryData();
			//并将数据存入到session域中
			//为了将数据存入redis中 直接将其存入session域中
			session.setAttribute("orderProjectVo", orderProjectVo);
			
		}
		//跳转到confirm_return页面
		return "confirm_return";
	}
}
