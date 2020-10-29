package com.atguigu.crowd.mvc.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.atguigu.crowd.entity.Admin;

/**
 * 
 * 考虑到User对象中仅仅包含用户名，密码，为了能够获取到原生的Admin对象，所以创建这个类对User进行扩展
 * @author User
 *
 */
public class SecurityAdmin extends User {

	//private static final long serialVersionUID = 1L;

	//原始的admin对象 里面包含admin对象的全部属性 包括用户名密码
	private Admin originalAdmin;

	public SecurityAdmin(
			//传入原始的admin对象
			Admin originalAdmin, 
			//传入admin角色权限信息的集合  
			List<GrantedAuthority> authorities) {
		//调用父类构造器
		super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);

		//给本类的this.originalAdmin赋值
		//将传入的Admin赋值给originalAdmin ，方便后面我们在页面中调用principal.originalAdmin.userName
		//来获取admin的信息
		this.originalAdmin = originalAdmin;
		
		//将密码设置为空（并不影响登录认证）
		//增加系统的安全性，避免密码泄露，目的是在前台页面查询不到密码
		this.originalAdmin.setUserPswd(null);
	}

	//对外提供获取原始的admin对象的get方法
	public Admin getOriginalAdmin() {

		return originalAdmin;
	}

}
