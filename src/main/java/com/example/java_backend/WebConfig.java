package com.example.java_backend.config;

import com.example.java_backend.interceptors.JwtAuthenticationInterceptor;
// import com.example.java_backend.interceptors.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationInterceptor jwtAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 2️⃣ JWT interceptor - chỉ chạy cho API cần auth
        registry.addInterceptor(jwtAuthenticationInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/auth/**",   // login, refresh, logout
                        "/api/hello",     // public API
                        "/error"          // Spring error
                );
    }
}
