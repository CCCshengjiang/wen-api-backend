package com.wen.wenapiinterface;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wen
 */
@SpringBootApplication
@MapperScan("com.wen.wenapiinterface.mapper")
public class WenApiInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WenApiInterfaceApplication.class, args);
    }

}
