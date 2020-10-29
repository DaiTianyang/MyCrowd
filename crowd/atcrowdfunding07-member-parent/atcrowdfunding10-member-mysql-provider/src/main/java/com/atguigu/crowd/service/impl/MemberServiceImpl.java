package com.atguigu.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.po.MemberPOExample;
import com.atguigu.crowd.entity.po.MemberPOExample.Criteria;
import com.atguigu.crowd.mapper.MemberPOMapper;
import com.atguigu.crowd.service.MemberService;
import com.mysql.fabric.xmlrpc.base.Member;

@Transactional(readOnly=true)
@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberPOMapper memberPOMapper;

	/**
	 * 根据传入用户账号查询出对应的MemberPO对象
	 */
	@Override
	public MemberPO getMemberPOByLoginAcctRemote(String loginAcct) {
		
		MemberPOExample example = new MemberPOExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andLoginAcctEqualTo(loginAcct);
		
		List<MemberPO> list = memberPOMapper.selectByExample(example);
		
		if(list == null || list.size() == 0){
			
			return null;
		}
		MemberPO memberPO = list.get(0);
		
		return memberPO;
	}

	@Transactional(
			propagation=Propagation.REQUIRES_NEW,
			rollbackFor=Exception.class,
			readOnly=false)
	@Override
	public void saveMember(MemberPO memberPO) {
		memberPOMapper.insertSelective(memberPO);
	}
	
}
