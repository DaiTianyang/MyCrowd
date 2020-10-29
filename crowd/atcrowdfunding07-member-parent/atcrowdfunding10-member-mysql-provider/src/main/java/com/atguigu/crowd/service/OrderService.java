package com.atguigu.crowd.service;

import java.util.List;

import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.entity.vo.OrderProjectVo;
import com.atguigu.crowd.entity.vo.OrderVO;

public interface OrderService {

	OrderProjectVo getOrderProjectVo(Integer projectId, Integer returnId);

	List<AddressVO> getAddressVOList(Integer memberId);

	void saveAddress(AddressVO addressVO);

	void saveOrder(OrderVO orderVO);

}
