package com.atguigu.crowd.service.api;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atguigu.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

public interface AdminService {

	public void saveAdmin(Admin admin);

	public List<Admin> getAll();

	public Admin getAdminByLoginAcct(String loginAcct, String userPswd);
	
	PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

	void removeAdmin(Integer adminId);

	Admin getAdminById(Integer adminId);

	void updateAdmin(Admin admin);

	void deleteAll(List<Integer> id);

	void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

	public Admin getAdminByLoginAcct(String username);
}
