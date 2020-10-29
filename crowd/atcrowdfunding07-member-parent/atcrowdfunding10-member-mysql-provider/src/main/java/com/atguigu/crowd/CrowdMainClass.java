package com.atguigu.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * 主启动类
 * 
 * @author User
 *
 */
@EnableEurekaClient
@MapperScan("com.atguigu.crowd.mapper")
@SpringBootApplication
public class CrowdMainClass {

	public static void main(String[] args) {
		SpringApplication.run(CrowdMainClass.class, args);
	}

}
