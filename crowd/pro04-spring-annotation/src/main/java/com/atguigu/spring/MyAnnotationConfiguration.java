package com.atguigu.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atguigu.spring.entity.Employee;

/**
 * @author User
 * 表示当前类是一个配置类，作用相当于以前的spring-context.xml这样的配置文件
 */
@Configuration
public class MyAnnotationConfiguration {
	//@Bean相当于做了下面xml标签的配置，把方法的返回值放到ioc容器中
	//<bean id="Employee" class="com.atguigu.spring.entity.Employee">
	@Bean
	public Employee getEmployee(){
		
		return new Employee();
	}
}
