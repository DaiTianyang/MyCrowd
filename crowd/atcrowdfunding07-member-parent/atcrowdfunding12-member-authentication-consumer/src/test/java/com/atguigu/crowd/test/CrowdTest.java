package com.atguigu.crowd.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aliyun.api.gateway.demo.util.HttpUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrowdTest {

	private Logger logger = LoggerFactory.getLogger(CrowdTest.class);
	@Test
	public void sendMessageTest(){
		
		String host = "https://smssend.shumaidata.com";
	    String path = "/sms/send";
	    String method = "POST";
	    String appcode = "e28d67887269409a851de4fb3c50d02a";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("receive", "15645693659");
	    querys.put("tag", "888888");
	    querys.put("templateId", "M09DD535F4");
	    Map<String, String> bodys = new HashMap<String, String>();


	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	
	    	StatusLine statusLine = response.getStatusLine();
	    	
	    	int code = statusLine.getStatusCode();
	    	logger.info("code="+code);
	    	String reasonPhrase = statusLine.getReasonPhrase();
	    	logger.info("reasonPhrase="+reasonPhrase);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}
