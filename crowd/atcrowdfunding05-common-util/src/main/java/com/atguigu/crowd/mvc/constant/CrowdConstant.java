package com.atguigu.crowd.mvc.constant;

import java.util.Collection;

public class CrowdConstant {


	public static final String MESSAGE_NOACCT_NO_PAWD = "登录失败！请输入账号密码再进行操作！";
	public static final String MESSAGE_LOGIN_FAILED = "登录失败！请确认账号密码是否正确！";
	public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "抱歉！这个账号已经被使用";
	public static final String MESSAGE_ACCESS_FORBIDEN = "请登录后再访问";
	public static final String MESSAGE_STRING_INVALIDATE = "字符串不合法！请不要输入空字符";
	public static final Object MESSAGE_CODE_NOT_EXISTS = "验证码过期,请检查手机号是否正确或重新发送验证码!";
	public static final Object MESSAGE_CODE_INVALID = "验证码不正确,请重新检查并输入!";
	public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统错误！登录账号不唯一";
	public static final String MESSAGE_LOGIN_DENIED = "抱歉！您没有权限进行访问！";
	public static final Object MESSAGE_HEADER_PIC_UPLOAD_FAILED = "文件上传失败";
	public static final Object MESSAGE_HEADER_PIC_EMPTY = "头图不能为空";
	public static final Object MESSAGE_DETAIL_PIC_EMPTY = "详情图不能为空";
	public static final String MESSAGE_TEMPLE_PROJECT_MISSING = "临时存储的ProjectVO对象丢失！";
	
	
	public static final String ATTR_NAME_EXCEPTION = "exception";
	public static final String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
	public static final String ATTR_NAME_LOGIN_MEMBER = "loginMember";
	public static final String ATTR_NAME_PAGE_INFO = "pageInfo";
	public static final String ATTR_NAME_MESSAGE = "message";
	public static final String ATTR_NAME_TEMPLE_PROJECT = "tempProject";
	public static final String ATTR_NAME_PORTAL_DATA = "portal_data";
	
	
	
	public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX";
	
	
	
	
	
	
	

}
