package com.kkreal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置类
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("KKReal API文档")
                        .description("基于Spring Boot 3 + MyBatis-Plus的RESTful API接口文档")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("KKReal Team")
                                .email("contact@kkreal.com")
                                .url("https://www.kkreal.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
