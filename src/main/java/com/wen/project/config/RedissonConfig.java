package com.wen.project.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson的配置
 *
 * @author wen
 */
@Configuration
@ConfigurationProperties(value = "spring.data.redis")
@Data
public class RedissonConfig {
    /**
     * redis地址
     */
    private String host;

    /**
     * redis端口号
     */
    private String port;

    /**
     * redis密码
     */
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        // 1. 创建配置对象
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%s", host, port);
        config.useSingleServer().setAddress(redisAddress).setDatabase(1).setPassword(password);
        // 2. 创建Redisson实例
        // Sync and Async API
        return Redisson.create(config);
    }

}