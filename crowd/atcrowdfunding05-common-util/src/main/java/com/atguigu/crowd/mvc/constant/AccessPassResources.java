package com.atguigu.crowd.mvc.constant;

import java.util.HashSet;
import java.util.Set;

public class AccessPassResources {

	
	public static final Set<String> PASS_RESOURCES_SET = new HashSet<>();
	/**
	 * 放行特定请求地址
	 */
	static{
		//放行首页
		PASS_RESOURCES_SET.add("/");
		//放行去注册页
		PASS_RESOURCES_SET.add("/auth/member/to/reg/page");
		//放行去登录页面
		PASS_RESOURCES_SET.add("/auth/member/to/login/page");
		//放行退出登录
		PASS_RESOURCES_SET.add("/auth/member/do/logout");
		//放行执行登录
		PASS_RESOURCES_SET.add("/auth/member/do/login");
		//放行执行注册
		PASS_RESOURCES_SET.add("/auth/do/member/register");
		//放行发送验证码
		PASS_RESOURCES_SET.add("/auth/member/send/short/message.json");
		//PASS_RESOURCES_SET.add("/");
		//放行首页众筹项目的详细信息
		PASS_RESOURCES_SET.add("/get/project/detail/{projectId}");
	}
	
	public static final Set<String> STATIC_RESOURCES_SET = new HashSet<>();
	
	static{
		/**
		 * 放行静态资源
		 */
		STATIC_RESOURCES_SET.add("bootstrap");
		STATIC_RESOURCES_SET.add("css");
		STATIC_RESOURCES_SET.add("fonts");
		STATIC_RESOURCES_SET.add("img");
		STATIC_RESOURCES_SET.add("jquery");
		STATIC_RESOURCES_SET.add("layer");
		STATIC_RESOURCES_SET.add("script");
		STATIC_RESOURCES_SET.add("ztree");
	}
	
	/**
	 * 
	 * 用于判断某个servletPath的值是否对应静态资源
	 * 返回值：
	 * 		true:是静态资源
	 * 		false:不是静态资源
	 * @param servletPath
	 * @return
	 */
	public static boolean judgeCurrentServletPathWetherStaticResource(String servletPath){
		//判断传入的String是不是空字符
		if(servletPath == null || servletPath.length() == 0){
			throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
		}
		//对字符串进行拆分
		/**
		 * 	拆分规则
		 *	"/aaa/bbb/ccc"按照"/"进行拆分然后存入数组后是
		 *	["","aaa","bbb","ccc"]
		 *	会把第一个"/"前面的空白给拆成一个空字符串存入数组中
		 *
		 */
		String[] split = servletPath.split("/");
		String firstLevelPath = split[1];
		//判断第一级目录是否为静态资源STATIC_RESOURCES_SET
		return STATIC_RESOURCES_SET.contains(firstLevelPath);
	}
	/**
	 * 对上述方法的简单测试！
	 */
/*	public static void main(String[] args) {
		String servletPath = "/css/aaa/bbb";
		boolean result = judgeCurrentServletPathWetherStaticResource(servletPath);
		System.out.println(result);
	}*/
}
