package com.atguigu.security.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.atguigu.security.entity.Admin;

@Component
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private JdbcTemplate jdbctemplate;
	/**
	 * 根据表单提交的用户名查询User对象，并且装配角色，权限等信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//1.从数据库查询admin对象信息
		String sql="select id,loginacct,userpswd,username,email from t_admin where loginacct= '"+username+"'";
		
		//Map<String, Object> resultMap = jdbctemplate.queryForMap(sql, username);
		List<Admin> list = jdbctemplate.query(sql, new BeanPropertyRowMapper<>(Admin.class));
		
		Admin admin = list.get(0);
		String userpswd = admin.getUserpswd();
		
		System.out.println(admin);
		
		//2.给admin设置权限信息
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		authorities.add(new SimpleGrantedAuthority("UPDATE"));
		
		//将用户信息和权限信息封装到UserDetails中
		
		return new User(username, userpswd, authorities);
	}
	

}
