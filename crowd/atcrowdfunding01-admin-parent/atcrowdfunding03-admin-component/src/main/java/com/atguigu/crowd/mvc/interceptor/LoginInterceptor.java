package com.atguigu.crowd.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.mvc.constant.CrowdConstant;
import com.atguigu.crowd.mvc.exception.AccessForbiddenException;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object args) throws Exception {
		// 1.通过request对象获取session对象
		HttpSession session = request.getSession();

		// 2.尝试从session域中获取admin
		Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);

		// 3.判断Admin是否为空
		if (admin == null) {
			// 4.抛出异常
			throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
		}

		// 5.如果Admin对象不为空，则返回true放行
		return true;
	}

}
