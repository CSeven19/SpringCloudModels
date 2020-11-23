package com.lsw.springcloud.configbean;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 * @apiNote RestTemplate用于模拟发送REST的客户端请求
 */
@Configuration
public class ConfigBean {
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
	// 指定轮询策略
//	@Bean
//	public IRule myRule(){
//		return new RoundRobinRule(); 	//轮询策略
//	}
}