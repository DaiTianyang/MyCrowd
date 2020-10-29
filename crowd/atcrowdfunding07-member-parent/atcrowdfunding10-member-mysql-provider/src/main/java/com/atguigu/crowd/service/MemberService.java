package com.atguigu.crowd.service;

import com.atguigu.crowd.entity.po.MemberPO;

public interface MemberService {

	MemberPO getMemberPOByLoginAcctRemote(String loginAcct);

	void saveMember(MemberPO memberPO);

}
