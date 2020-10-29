package com.atguigu.crowd.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.entity.vo.OrderProjectVo;
import com.atguigu.crowd.entity.vo.OrderVO;
import com.atguigu.crowd.mapper.OrderProjectPoMapper;
import com.atguigu.crowd.service.OrderService;
import com.atguigu.crowd.util.ResultEntity;

@RestController
public class OrderProviderHandler {

	@Autowired
	private OrderService orderService;
	
	
	/**
	 * 把订单信息保存到数据库
	 * @param orderVO
	 * @return
	 */
	@RequestMapping("/save/order/remote")
	public ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO){
		try {
			orderService.saveOrder(orderVO);
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	/**
	 * 把前台提交的表单数据（AddressVO）进行保存
	 * @param addressVO
	 * @return
	 */
	@RequestMapping("/save/address/remote")
	public ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO){
		
		try {
			orderService.saveAddress(addressVO);
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	/**
	 * 根据传过来的memberId来查询该用户的收货地址信息
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/get/address/vo/remote")
	public ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId){
		
		try {
			List<AddressVO> addressVOList = orderService.getAddressVOList(memberId);
			return ResultEntity.successWithData(addressVOList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	
	/**
	 * 根据projectId和returnId查询出对应的OrderProjectVo对象
	 * 如果查询成功则将该数据返回
	 * 查询失败返回错误信息
	 * @param projectId
	 * @param returnId
	 * @return
	 */
	@RequestMapping("/get/order/project/vo/remote")
	public ResultEntity<OrderProjectVo> getOrderProjectVoRemote(
			@RequestParam("projectId") Integer projectId,
			@RequestParam("returnId") Integer returnId){
		
		try {
			OrderProjectVo orderProjectVo = orderService.getOrderProjectVo(projectId,returnId);
			return ResultEntity.successWithData(orderProjectVo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
}
