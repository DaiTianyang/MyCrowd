package com.atguigu.crowd.mvc.handler;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.ResultEntity;

@Controller
public class TestHandler {

	@Autowired
	private AdminService adminService;
	/*@RequestMapping("/test/ssm.html")
	public String testInsertAdmin(){
		Integer i = 10;
		String name = "jack";
		String email = name+"163.com"; 
		for (int j = 0; j < 100; j++) {
			Admin admin = new Admin(i, name, "123123", name, email,null);
			i++;
			name = name+i;
			email = name+"163.com"; 
		}
		
		adminService.saveAdmin(admin);
		return "";
	}*/
	@RequestMapping("/test/ssm.html")
	public String testSsm(Model model) {
		List<Admin> adminList = adminService.getAll();
		model.addAttribute("adminList", adminList);
		System.out.println(10 / 0);
		return "success";
	}

	@ResponseBody
	@RequestMapping("/send/array.html")
	public String reserviceArray(@RequestBody List<Integer> list) {
		for (Integer arrays : list) {
			System.out.println(arrays);
		}
		return "success";
	}

	/*@ResponseBody
	@RequestMapping("/send/student.json")
	public ResultEntity<Student> reserviceStudent(@RequestBody Student student) {
		System.out.println(student);
		String a = null;
		//System.out.println(a.length());
		return ResultEntity.successWithData(student);
	}*/
	
	@ResponseBody
	@RequestMapping("/test/ajax/async.html")
	public String Async() throws InterruptedException{
		Thread.sleep(2000);
		return "success";
	}
}
