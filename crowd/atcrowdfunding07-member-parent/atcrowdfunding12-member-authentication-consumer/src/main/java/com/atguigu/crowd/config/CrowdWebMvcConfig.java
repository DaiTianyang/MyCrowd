package com.atguigu.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//拦截地址
		String urlPath = "/auth/member/to/reg/page";
		//返回的视图地址
		String viewName = "member-reg";
		//配置一个ViewController
		registry.addViewController(urlPath).setViewName(viewName);
		registry.addViewController("/auth/member/to/login/page").setViewName("member-login");
		registry.addViewController("/auth/member/to/center/page").setViewName("member-center");
		///member/my/crowd
		registry.addViewController("/member/my/crowd").setViewName("member-crowd");

	}

}
