package com.wen.wenapiclient;

import com.wen.wenapiclient.client.WenApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * api 客户端配置类
 *
 * @author wen
 */
@Configuration
@ConfigurationProperties("wen.api.client")
@Data
@ComponentScan
public class WenApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public WenApiClient wenApiClient() {
        return new WenApiClient(accessKey, secretKey);
    }

}
