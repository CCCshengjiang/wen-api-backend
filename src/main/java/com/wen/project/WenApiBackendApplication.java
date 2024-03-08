package com.wen.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wen
 */
@SpringBootApplication
@MapperScan("com.wen.project.mapper")
public class WenApiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WenApiBackendApplication.class, args);
    }

}
