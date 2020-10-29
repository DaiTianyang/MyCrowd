package com.atguigu.crowd.mvc.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.atguigu.crowd.mvc.constant.CrowdConstant;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	/**
	 * 这里不能用@Bean的方法将BCryptPasswordEncoder加入IOC容器
	 * 因为：
	 * 加入IOC容器后 加入的是springmvc的IOC容器 然后配置加密过程的时候spring自动注入BCryptPasswordEncoder会报错
	 * 报错为在容器中找不到这个bean原因是父子容器的原因
	 * 所以我们在spring配置文件中加入BCryptPasswordEncoder的Bean
	 * 就可以在service层自动注入也可以在该配置文件中直接自动注入
	 */
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	//把盐值加密加入IOC容器中
	/*@Bean
	public BCryptPasswordEncoder getPasswordEncoder(){
		
		return new BCryptPasswordEncoder();
	}*/
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		//		super.configure(auth);
		/*builder
		.inMemoryAuthentication()
		.withUser("tom")
		.password("123123")
		.roles("ADMIN");*/
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		//super.configure(http);
		//对请求进行授权
		security
		.authorizeRequests()
		.antMatchers("/admin/to/login/page.html")	//放行登录页面
		.permitAll()
		.antMatchers("/bootstrap/**")
		.permitAll()
		.antMatchers("/crowd/**")
		.permitAll()
		.antMatchers("/css/**")
		.permitAll()
		.antMatchers("/fonts/**")
		.permitAll()
		.antMatchers("/img/**")
		.permitAll()
		.antMatchers("/jquery/**")
		.permitAll()
		.antMatchers("/layer/**")
		.permitAll()
		.antMatchers("/script/**")
		.permitAll()
		.antMatchers("/ztree/**")
		.permitAll()
		.antMatchers("/admin/get/page.html")
		//.hasRole("经理")
		.access("hasRole('经理') OR hasAuthority('user:get')")
		.anyRequest()					//其他请求
		.authenticated()				//登录后访问
		.and()
		.exceptionHandling()
		.accessDeniedHandler(new AccessDeniedHandler() {
			
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException arg2)
					throws IOException, ServletException {
				request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_LOGIN_DENIED));
				request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
			}
		})
		.and()
		.csrf()
		.disable()
		.formLogin()					//开启登录功能
		.loginPage("/admin/to/login/page.html")		//指定登录页面
		.loginProcessingUrl("/security/do/login.html")	//指定处理登录请求的url地址
		.defaultSuccessUrl("/admin/to/main/page.html")	//指定登录成功后登录的地址
		.usernameParameter("loginAcct")		//账号的请求参数名称
		.passwordParameter("userPswd")		//密码的请求参数名称
		.and()
		.logout()							//开启退出登录功能
		.logoutUrl("/security/do/logout.html")						//指定退出登录的地址
		.logoutSuccessUrl("/admin/to/login/page.html")				//退出成功后前往的地址
		;
		
	}
}
