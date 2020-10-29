package com.atguigu.crowd.mvc.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Auth;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.AuthService;
import com.atguigu.crowd.service.api.RoleService;

/**
 * 封装权限的整体思路就是：
 * 首先获取到admin的账号 通过账号查询出admin对象
 * 在通过admin的id查询出对应的角色id
 * 在根据角色id查询出权限的authName
 * 在通过调用写好的securityAdmin类对其进行封装进User对象
 * 再返回一个user对象
 * @author User
 *
 */
@Component
public class CrowdUserDetailsService implements UserDetailsService{

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//根据输入LoginAcct的查询admin信息
		Admin admin = adminService.getAdminByLoginAcct(username);
		//id
		Integer adminId = admin.getId();
		//查询出角色信息
		List<Role> roleList = roleService.getAssignedRole(adminId);
		//查询出权限信息
		List<String> authList= authService.getAssignedAuthNameByAdminId(adminId);
 
		//创建存储角色和权限信息的数组
		List<GrantedAuthority> authorities = new ArrayList<>();
		//遍历存入角色信息
		for (Role role : roleList) {
			//千万别忘记加前缀
			String roleName = "ROLE_"+role.getName();
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
			authorities.add(simpleGrantedAuthority);
		}
		//遍历存入权限信息
		for (String authName : authList) {
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
			authorities.add(simpleGrantedAuthority);
		}
		
		//封装进user
		SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);
		return securityAdmin;
	}

}
