package com.atguigu.crowd.mvc.exception;

/**
 * 保存或更新Admin时如果监测到登录账号重复抛出此异常
 * @author User
 *
 */
public class LoginAcctAlreadyInUseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginAcctAlreadyInUseException() {
		super();
	}

	public LoginAcctAlreadyInUseException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public LoginAcctAlreadyInUseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseException(String arg0) {
		super(arg0);
	}

	public LoginAcctAlreadyInUseException(Throwable arg0) {
		super(arg0);
	}

}
