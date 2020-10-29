package com.atguigu.crowd.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.OrderProjectVo;
import com.atguigu.crowd.entity.vo.OrderVO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.entity.vo.ProjectVO;
import com.atguigu.crowd.util.ResultEntity;
/**
 * 连接mysql远程接口
 * @author User
 *
 */
@FeignClient("atguigu-crowd-mysql")
public interface MySQLRemoteService {

	@RequestMapping("/get/memberpo/by/login/acct/remote")
	ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginAcct") String loginAcct);
	/**
	 * 添加member的远程接口
	 * @param memberPO
	 * @return
	 */
	@RequestMapping("/save/member/remote")
	public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO);
	
	
	/**
	 * 提交保存发起众筹项目的远程接口
	 * @param projectVO
	 * @param memberId 
	 * @return
	 */
	@RequestMapping("/save/project/vo/remote")
	ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO,@RequestParam("memberId") Integer memberId);
	
	
	/**
	 * 获取首页数据的远程接口
	 * @return
	 */
	@RequestMapping("/get/portal/type/project/data/remote")
	public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote();
	
	
	/**
	 * 通过首页点击众筹项目来获取对应众筹项目的详细信息的远程接口
	 * @return
	 */
	@RequestMapping("/get/project/detail/remote/{projectId}")
	public ResultEntity<DetailProjectVO> getDetailProjectVODataRemote(@PathVariable("projectId") Integer projectId);
	
	
	/**
	 * 通过在众筹项目的详情页面点击支持来进行回显项目数据的远程接口
	 * 会跳转到confirm_return页面并显示对应的数据
	 * @param projectId
	 * @param returnId
	 * @return
	 */
	@RequestMapping("/get/order/project/vo/remote")
	public ResultEntity<OrderProjectVo> getOrderProjectVoRemote(@RequestParam("projectId") Integer projectId,@RequestParam("returnId") Integer returnId);
	
	
	
	/**
	 * 通过memberId调用远程接口进行查询该用户的收获地址信息并返回给页面存入session域中
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/get/address/vo/remote")
	public ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId);
	
	
	/**
	 * 保存新添加的address的远程接口
	 * @param addressVO
	 * @return
	 */
	@RequestMapping("/save/address/remote")
	public ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO);
	
	
	/**
	 * 把订单数据保存到数据库
	 * @param orderVO
	 * @return
	 */
	@RequestMapping("/save/order/remote")
	public ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO);
	
}
