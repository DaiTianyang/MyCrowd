package com.atguigu.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
/**
 * @author User
 *	注意一定要将这个类放到自动扫描包下，否则这个类不会生效
 */
//將这个类标记为配置类
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

@Configuration

// 启动web环境下的权限控制功能
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

	// 自动装配一下数据源
	@Autowired
	private DataSource datasource;

	/**
	 * 普通的密码加密比对
	 */
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	/*@Autowired
	private MyPasswordEncoder myPasswordEncoder;*/
	/**
	 * 加入盐值的比对密码
	 */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder(){
		
		return new BCryptPasswordEncoder();
	}
	/**
	 * 设置固定的账号密码，并执行账号密码的检查
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {

/*		builder.inMemoryAuthentication() // 在内存中完成账号、密码的检查
				.withUser("tom") // 指定账号
				.password("123123") // 指定密码
				.roles("ADMIN") // 指定我们当前用户的角色 角色权限的区别： 角色会在后台自动加入ROLE_ 而权限不会
				.and().withUser("lilei").password("123123").roles("UPDATE", "学徒") // 指定当前用户的权限
		;*/
		
		//builder.userDetailsService(userDetailsService).passwordEncoder(myPasswordEncoder);
		builder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

	}

	@Override
	protected void configure(HttpSecurity security) throws Exception {

		// 创建tokenRepository对象
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		// 装配数据源
		tokenRepository.setDataSource(datasource);
		tokenRepository.setCreateTableOnStartup(true);

		security.authorizeRequests() // 对请求进行授权
				.antMatchers("/index.jsp") // 针对/index.jsp路径进行授权
				.permitAll() // 可以无条件访问
				.antMatchers("/layui/**") // 放行静态资源
				.permitAll().antMatchers("/level1/**") // 针对/level1/**下的所有路径设置访问要求
				.hasRole("ADMIN") // 必须拥有‘学徒’权限才能进行访问
				.antMatchers("/level2/**").hasRole("大师").antMatchers("/level3/**").hasRole("宗师").and()
				.authorizeRequests() // 对请求进行授权
				.anyRequest() // 任意的请求
				.authenticated() // 需要登录后才能进行访问
				.and().formLogin() // 使用表单形式登录
				// index.jsp GET - the login form 去登录页面
				// index.jsp POST - process the credentials and if valid
				// authenticate the user 提交登录表单
				// index.jsp?error GET - redirect here for failed authentication
				// attempts 登录失败
				// index.jsp?logout GET - redirect here after successfully
				// logging out 退出登录
				.loginPage("/index.jsp") // 指定登录页面，如果没有指定则使用security自带的登录页面
				// loginProcessingUrl()指定了登录地址，则上面设置的loginPage()就会被其覆盖掉
				.loginProcessingUrl("/do/login.html") // 指定登录提交表单的地址
				// .permitAll()
				.usernameParameter("loginAcct") // 登录账号的请求参数名
				.passwordParameter("userPswd") // 登录密码的请求参数名
				.defaultSuccessUrl("/main.html") // 登录成功后默认访问的url地址
				.and()
				/*
				 * .csrf() .disable()
				 */ // 禁用CSRF功能
				.logout() // 开启退出功能
				.logoutUrl("/do/logout.html") // 指定退出的URL地址
				.logoutSuccessUrl("/index.jsp") // 退出成功跳转页面
				.and().exceptionHandling() // 配置拒绝访问403页面
				.accessDeniedPage("/to/no/auth/page.html") // 访问被拒绝前往的页面
				.accessDeniedHandler(new AccessDeniedHandler() {

					@Override
					public void handle(HttpServletRequest request, HttpServletResponse response,
							AccessDeniedException accessDeniedException) throws IOException, ServletException {
						request.setAttribute("message", accessDeniedException.getMessage());
						request.getRequestDispatcher("/WEB-INF/views/no_auth.jsp").forward(request, response);

					}
				}).and().rememberMe().tokenRepository(tokenRepository);

	}
}
