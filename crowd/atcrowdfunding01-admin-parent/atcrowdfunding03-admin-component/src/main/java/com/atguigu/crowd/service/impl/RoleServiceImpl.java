package com.atguigu.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.entity.RoleExample;
import com.atguigu.crowd.entity.RoleExample.Criteria;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


/**
 * @author User
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
		// 1.开启分页
		PageHelper.startPage(pageNum, pageSize);

		// 2.查询数据
		List<Role> list = roleMapper.selectRoleByKeyword(keyword);

		// 3.封装到PageInfo对象中

		return new PageInfo<>(list);
	}

	@Override
	public void save(Role role) {
		
		roleMapper.insert(role);
	}

	@Override
	public void update(Role role) {
		
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public void delete(List<Integer> rolelist) {
		
		RoleExample example = new RoleExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andIdIn(rolelist);
		
		
		roleMapper.deleteByExample(example);
		
	}

	@Override
	public List<Role> getAssignedRole(Integer adminId) {
		
		return roleMapper.selectAssignedRole(adminId);
	}

	@Override
	public List<Role> getUnAssignedRole(Integer adminId) {
		
		return roleMapper.selectUnAssignedRole(adminId);
	}

}
