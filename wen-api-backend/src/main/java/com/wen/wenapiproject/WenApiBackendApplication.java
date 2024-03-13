package com.wen.wenapiproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wen
 */
@SpringBootApplication
@MapperScan("com.wen.wenapiproject.mapper")
public class WenApiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WenApiBackendApplication.class, args);
    }

}
