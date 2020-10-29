package com.atguigu.crowd.entity.vo;

import java.io.Serializable;

public class MemberLoginVO implements Serializable{

	private Integer id;
	
	private String loginAcct;
	
	private String email;

	private String userName;

	
	
	public MemberLoginVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberLoginVO(Integer id, String loginAcct, String email, String userName) {
		super();
		this.id = id;
		this.loginAcct = loginAcct;
		this.email = email;
		this.userName = userName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "MemberLoginVO [id=" + id + ", loginAcct=" + loginAcct + ", email=" + email + ", userName=" + userName
				+ "]";
	}
	
	
	
	
	
	
}
