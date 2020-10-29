package com.atguigu.crowd.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import com.atguigu.crowd.entity.po.MemberPO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

	private Logger logger = LoggerFactory.getLogger(RedisTest.class);

	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Test
	public void TestRedis() {
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		operations.set("msg", "helloworld");
	}
}
