package com.wen.wenapiproject.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义Swagger主页信息
 *
 * @author wen
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI swaggerOpenApi() {
        return new OpenAPI()
                .info(new Info().title("wen-api")
                        .description("aip 接口平台")
                        .version("v1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("作者信息")
                        .url("https://github.com/CCCshengjiang"));
    }
}