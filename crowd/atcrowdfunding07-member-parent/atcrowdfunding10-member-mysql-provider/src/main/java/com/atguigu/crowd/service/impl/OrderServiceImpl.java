package com.atguigu.crowd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crowd.entity.po.AddressPo;
import com.atguigu.crowd.entity.po.AddressPoExample;
import com.atguigu.crowd.entity.po.AddressPoExample.Criteria;
import com.atguigu.crowd.entity.po.OrderPo;
import com.atguigu.crowd.entity.po.OrderProjectPo;
import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.entity.vo.OrderProjectVo;
import com.atguigu.crowd.entity.vo.OrderVO;
import com.atguigu.crowd.mapper.AddressPoMapper;
import com.atguigu.crowd.mapper.OrderPoMapper;
import com.atguigu.crowd.mapper.OrderProjectPoMapper;
import com.atguigu.crowd.service.OrderService;

@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderProjectPoMapper orderProjectPoMapper;
	
	@Autowired
	private OrderPoMapper orderPoMapper;
	
	@Autowired
	private AddressPoMapper addressPoMapper;

	@Override
	public OrderProjectVo getOrderProjectVo(Integer projectId, Integer returnId) {
		
		return orderProjectPoMapper.selectOrderProjectVO(returnId);
	}

	@Override
	public List<AddressVO> getAddressVOList(Integer memberId) {
		
		AddressPoExample example = new AddressPoExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andMemberIdEqualTo(memberId);
		
		List<AddressPo> addressPOList = addressPoMapper.selectByExample(example);
		
		List<AddressVO> addressVOList = new ArrayList<AddressVO>();
		
		for (AddressPo addressPo : addressPOList) {
			AddressVO addressVO = new AddressVO();
			BeanUtils.copyProperties(addressPo, addressVO);
			addressVOList.add(addressVO);
		}
		//最终要返回vo对象
		return addressVOList;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	@Override
	public void saveAddress(AddressVO addressVO) {
		AddressPo addressPo = new AddressPo();
		BeanUtils.copyProperties(addressVO, addressPo);
		addressPoMapper.insertSelective(addressPo);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	@Override
	public void saveOrder(OrderVO orderVO) {
		OrderPo orderPO = new OrderPo();
		
		BeanUtils.copyProperties(orderVO, orderPO);
		
		OrderProjectPo orderProjectPo = new OrderProjectPo();
		
		BeanUtils.copyProperties(orderVO.getOrderProjectVo(), orderProjectPo);
		
		orderPoMapper.insert(orderPO);
		//因为数据库存储orderProjectPo需要一个orderID 所以将orderid取出来放到orderProjectPo中在对orderProjectPo进行存储
		Integer id = orderPO.getId();
		
		orderProjectPo.setOrderId(id);
		
		orderProjectPoMapper.insert(orderProjectPo);
	}
	
	
}
