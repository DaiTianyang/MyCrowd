package com.atguigu.crowd.service.api;

import java.util.List;

import com.atguigu.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

public interface RoleService {

	PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

	void save(Role role);

	void update(Role role);

	void delete(List<Integer> rolelist);
	//查询已分配角色
	List<Role> getAssignedRole(Integer adminId);
	//查询未分配角色
	List<Role> getUnAssignedRole(Integer adminId);

}
