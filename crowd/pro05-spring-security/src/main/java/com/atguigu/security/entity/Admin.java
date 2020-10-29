package com.atguigu.security.entity;

public class Admin {

	private int id;
	private String loginacct;
	private String userpswd;
	private String username;
	private String email;

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(int id, String loginacct, String userpswd, String username, String email) {
		super();
		this.id = id;
		this.loginacct = loginacct;
		this.userpswd = userpswd;
		this.username = username;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginacct() {
		return loginacct;
	}

	public void setLoginacct(String loginacct) {
		this.loginacct = loginacct;
	}

	public String getUserpswd() {
		return userpswd;
	}

	public void setUserpswd(String userpswd) {
		this.userpswd = userpswd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", loginacct=" + loginacct + ", userpswd=" + userpswd + ", username=" + username
				+ ", email=" + email + "]";
	}

}
