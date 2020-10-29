package com.atguigu.crowd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Administrator
 * @Date 2020/5/21
 */
@Component
@ConfigurationProperties(prefix = "short.message")
public class ShortMessageProperties {
	private String host;
	private String path;
	private String method;
	private String appCode;
	private String templatedId;

	public ShortMessageProperties() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShortMessageProperties(String host, String path, String method, String appCode, String templatedId) {
		super();
		this.host = host;
		this.path = path;
		this.method = method;
		this.appCode = appCode;
		this.templatedId = templatedId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getTemplatedId() {
		return templatedId;
	}

	public void setTemplatedId(String templatedId) {
		this.templatedId = templatedId;
	}

	@Override
	public String toString() {
		return "ShortMessageProperties [host=" + host + ", path=" + path + ", method=" + method + ", appCode=" + appCode
				+ ", templatedId=" + templatedId + "]";
	}

}
