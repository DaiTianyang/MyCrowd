package com.atguigu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * 主启动类
 * 
 * @author User
 *
 */
@EnableZuulProxy
@SpringBootApplication
public class CrowdMainClass {

	public static void main(String[] args) {
		SpringApplication.run(CrowdMainClass.class, args);
	}

}
