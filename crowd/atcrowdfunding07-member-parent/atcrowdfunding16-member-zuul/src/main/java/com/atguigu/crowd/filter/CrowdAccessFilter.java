package com.atguigu.crowd.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.atguigu.crowd.entity.vo.MemberLoginVO;
import com.atguigu.crowd.mvc.constant.AccessPassResources;
import com.atguigu.crowd.mvc.constant.CrowdConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
/**
 * 记得加入容器中：@Component
 * @author User
 *
 */
@Component
public class CrowdAccessFilter extends ZuulFilter{

	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		
		HttpServletRequest request = requestContext.getRequest();
		
		HttpSession session = request.getSession();
		
		MemberLoginVO membervo = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		//判断获取到session域中的membervo是否为空如果为空则返回一个错误message并重定向到login页面
		if(membervo == null){
			HttpServletResponse response = requestContext.getResponse();
			//将报错存进session域的message
			session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
			//进行重定向
			try {
				response.sendRedirect("/auth/member/to/login/page");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 判断当前是否为特定需要放行的请求地址或静态资源
	 * 如果是则放行
	 * 如果不是则进行登录检测拦截
	 */
	@Override
	public boolean shouldFilter() {
		//获取RequestContext对象
		RequestContext requestContext = RequestContext.getCurrentContext();
		//通过RequestContext对象获取当前request请求对象
		//框架底层是借助ThreadLocal从当前线程上获取实现绑定的request对象
		HttpServletRequest request = requestContext.getRequest();
		//从request请求中获取servletPath
		String servletPath = request.getServletPath();
		
		boolean passResult = AccessPassResources.PASS_RESOURCES_SET.contains(servletPath);
		//判断servletPath是否为"放行特定请求地址" 
		//如果返回为true则代表是"放行特定请求地址"--放行 
		//如果返回为false则代表不是"放行特定请求地址" --拦截
		//return false;代表放行不执行上面的run方法
		if(passResult){
				
			return false;
		}
		
		boolean judgeStaticResource = AccessPassResources.judgeCurrentServletPathWetherStaticResource(servletPath);
		
		if(judgeStaticResource){
			
			return false;
		}
		//如果上面的判断返回的是false就代表既不是特定请求地址也不是静态资源页面 固：返回true进行登录检查拦截
		return true;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String filterType() {
		//在目标微服务前执行过滤
		return "pre";
		
	}

}
