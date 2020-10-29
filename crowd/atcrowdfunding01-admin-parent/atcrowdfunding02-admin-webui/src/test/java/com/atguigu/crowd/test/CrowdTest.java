package com.atguigu.crowd.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;

/**
 * 在类上标记相应的注解，spring整合Junit
 * 
 * @author User
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml" })
public class CrowdTest {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleMapper roleMapper;
	@Test
	public void testLog() {
		Logger logger = LoggerFactory.getLogger(CrowdTest.class);
		logger.debug("Hello I am Debug Level!!!");
		logger.info("Info Level!!!");
		logger.warn("Warn Level!!!");
		logger.error("Error Level!!!");
	}

	@Test
	public void testConnection() throws SQLException {
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
	}

	@Test
	public void selectById() {
		Admin admin = new Admin(null, "tom11", "123123", "汤姆", "tom@qq.com", null);
		int insert = adminMapper.insert(admin);
		System.out.println(insert);
		// Admin selectByPrimaryKey = adminMapper.selectByPrimaryKey(1);
		// System.out.println(selectByPrimaryKey);
	}

	@Test
	public void testTx() {
		Integer i = 2;
		String name = "jack";
		String email = name+"163.com"; 
		Admin admin = null;
		String source = "123123";
		String password = CrowdUtil.md5(source);
		System.out.println(password);
		for (int j = 0; j < 100; j++) {
			admin = new Admin(i, name, password, name, email,null);
			i++;
			name = "jack"+i;
			email = name+"@163.com"; 
			System.out.println(admin);
			adminService.saveAdmin(admin);
		}
		
		
	}
	@Test
	public void testAddRole(){
		for (int i = 0; i < 200; i++) {
			Role role = new Role(null, "部门经理"+i);
			roleMapper.insert(role);
			
		}
		
	}

}
