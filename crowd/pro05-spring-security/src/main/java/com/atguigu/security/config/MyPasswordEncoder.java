package com.atguigu.security.config;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

	/**
	 * 密码加密
	 */
	@Override
	public String encode(CharSequence rawPassword) {

		String algorithm = "MD5";
		try {

			// 1.创建messageDigest
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			// 2.获取rawPassword的字节数组
			byte[] input = ((String) rawPassword).getBytes();

			// 3.加密
			byte[] output = messageDigest.digest(input);

			// 6.创建BigInteger对象
			int signum = 1;
			BigInteger bigInteger = new BigInteger(signum, output);

			// 7.按照16进制将BigInteger的值转换为字符串
			int radix = 16;
			String encoded = bigInteger.toString(radix).toUpperCase();

			return encoded;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

			return null;
		}

	}

	/**
	 * 表单提交的密码和数据库的密码进行比对
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		/**
		 * rawPassword : 表单提交的密码
		 * 
		 * encodedPassword : 数据库的加密后的密码
		 */
		//获取到上面加密过后的密码 并跟数据库密码进行比对，返回比对结果
		String fromPassword = encode(rawPassword);
		return Objects.equals(fromPassword, encodedPassword);
	}
/*
	public static void main(String[] args) {
		String encode = new MyPasswordEncoder().encode("123123");
		System.out.println(encode);
	}*/

}
