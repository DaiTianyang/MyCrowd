package com.atguigu.crowd.api;

import java.util.concurrent.TimeUnit;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 连接redis远程接口
 * @author User
 *
 */
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crowd.util.ResultEntity;
@FeignClient("atguigu-crowd-redis")
public interface RedisRemoteService {
	/**
	 * setRedisKeyValueRemote
	 * 发送redis键值对
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping("/set/redis/key/value/remote")
	ResultEntity<String> setRedisKeyValueRemote(
			@RequestParam("key") String key,
			@RequestParam("value") String value);
	/**
	 * setRedisKeyValueRemoteWithTimeout
	 * 发送redis键值对，附加超时时间
	 * @param key
	 * @param value
	 * @param time
	 * @return
	 */
	@RequestMapping("/set/redis/key/value/remote/with/timeout")
	ResultEntity<String> setRedisKeyValueRemoteWithTimeout(
			@RequestParam("key") String key,
			@RequestParam("value") String value,
			@RequestParam("time") long time,
			@RequestParam("timeUnit") TimeUnit timeUnit);
	/**
	 * getRedisStringValueByKeyRemote
	 * 通过redis的key获取到value
	 * @param key
	 * @return
	 */
	@RequestMapping("/get/redis/string/value/by/key/remote")
	ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key);
	/**
	 * 
	 * removeRedisKeyRemote
	 * 根据redis的key进行删除
	 * @param key
	 * @return
	 */
	@RequestMapping("/remove/redis/key/remote")
	ResultEntity<String> removeRedisKeyRemote(@RequestParam("key") String key);

}
