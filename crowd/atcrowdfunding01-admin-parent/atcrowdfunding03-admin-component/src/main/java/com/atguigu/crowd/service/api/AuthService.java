package com.atguigu.crowd.service.api;

import java.util.List;
import java.util.Map;

import com.atguigu.crowd.entity.Auth;

public interface AuthService {

	//查询全部权限
	List<Auth> getAll();
	//通过角色id查询对应的权限
	List<Integer> getAssignedAuthIdByRoleId(Integer roleId);
	//保存权限和角色关系
	void saveRoleAuthRelathinship(Map<String, List<Integer>> map);
	//通过adminid查询对应的权限名
	List<String> getAssignedAuthNameByAdminId(Integer adminId);
	
}
