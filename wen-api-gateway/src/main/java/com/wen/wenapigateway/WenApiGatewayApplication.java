package com.wen.wenapigateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wen
 */
@SpringBootApplication
@EnableDubbo
public class WenApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WenApiGatewayApplication.class, args);
	}

}
