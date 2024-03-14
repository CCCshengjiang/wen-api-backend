package com.wen.wenapiproject;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wen
 */
@SpringBootApplication
@MapperScan("com.wen.wenapiproject.mapper")
@EnableDubbo
public class WenApiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WenApiBackendApplication.class, args);
    }

}
