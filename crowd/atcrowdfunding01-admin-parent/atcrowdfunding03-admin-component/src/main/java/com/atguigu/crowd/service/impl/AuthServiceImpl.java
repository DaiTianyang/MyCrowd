package com.atguigu.crowd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.entity.Auth;
import com.atguigu.crowd.entity.AuthExample;
import com.atguigu.crowd.mapper.AuthMapper;
import com.atguigu.crowd.service.api.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthMapper authMapper;

	@Override
	public List<Auth> getAll() {

		return authMapper.selectByExample(new AuthExample());
	}

	@Override
	public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {

		return authMapper.selectAssignedAuthIdByRoleId(roleId);
	}

	@Override
	public void saveRoleAuthRelathinship(Map<String, List<Integer>> map) {
		// 1，获取roleId的值
		List<Integer> roleList = map.get("roleId");
		Integer roleId = roleList.get(0);
		// 2.根据roleId删除旧的数据
		authMapper.deleteOldRelationship(roleId);
		// 3.获取authId
		List<Integer> authIdlist = map.get("authIdArray");
		// 4.判断authIdlist是否有效
		if (authIdlist != null && authIdlist.size() > 0) {
			authMapper.insertNewRelationship(roleId, authIdlist);
		}
	}

	@Override
	public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
		List<String> list = authMapper.selectgetAssignedAuthNameByAdminId(adminId);
		return list;
	}
}
