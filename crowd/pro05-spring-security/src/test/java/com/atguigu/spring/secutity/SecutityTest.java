package com.atguigu.spring.secutity;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecutityTest {

	
	public static void main(String[] args) {
		
		BCryptPasswordEncoder bcCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		String rawPassword = "123123";
		
		String encode = bcCryptPasswordEncoder.encode(rawPassword);
		//$2a$10$GYgpeiHzGCwS9DTTg1K9..FIE6gcqUm2jJkswyxq4bgrjp9d6P5/y
		//$2a$10$5tu9uAqX3wtSNe.t7oYk7OVPPL0u9qHej275xF4TD4VoT7vbjwtsy
		System.out.println(encode);
	}
	
	
}
class TestEncode{
	
	public static void main(String[] args) {
		String rawPassword = "123123";
		
		String encodPassword = "$2a$10$GYgpeiHzGCwS9DTTg1K9..FIE6gcqUm2jJkswyxq4bgrjp9d6P5/y";
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		boolean matches = passwordEncoder.matches(rawPassword, encodPassword);
		
		System.out.println(matches?"一致":"不一致");
	}
}
