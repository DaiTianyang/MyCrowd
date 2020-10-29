package com.atguigu.crowd.entity.vo;

public class MemberVO {

	private String loginAcct;
	
    private String userPswd;

    private String userName;

    private String email;
    
    private String phone;
    
    private String code;
    
    

	public MemberVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberVO(String loginAcct, String userPswd, String userName, String email, String phone, String code) {
		super();
		this.loginAcct = loginAcct;
		this.userPswd = userPswd;
		this.userName = userName;
		this.email = email;
		this.phone = phone;
		this.code = code;
	}

	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}

	public String getUserPswd() {
		return userPswd;
	}

	public void setUserPswd(String userPswd) {
		this.userPswd = userPswd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "MemberVO [loginAcct=" + loginAcct + ", userPswd=" + userPswd + ", userName=" + userName + ", email="
				+ email + ", phone=" + phone + ", code=" + code + "]";
	}
    
    
    
	
}
