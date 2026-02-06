package com.kkreal.config;

import com.kkreal.auth.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册认证拦截器，拦截除登录、注册等公共接口外的所有请求
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns(    // 排除公共接口
                        "/auth/login",
                        "/auth/register",
                        "/auth/refresh",
                        "/doc.html",           // Swagger UI
                        "/swagger-ui/**",      // Swagger UI resources
                        "/v3/api-docs/**",     // API文档
                        "/v3/api-docs.yaml",   // API文档 (YAML)
                        "/webjars/**",         // Swagger UI webjars
                        "/swagger-resources/**", // Swagger resources
                        "/actuator/**",        // Actuator endpoints (if enabled)
                        "/error"               // Error pages
                );
    }
}
