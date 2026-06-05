package com.lin.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 设置全局API路径前缀
        configurer.addPathPrefix("/api", c -> true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源映射
        // 将 /data/** 路径映射到 classpath:/data/ 目录
        registry.addResourceHandler("/data/**")
                .addResourceLocations("classpath:/data/");
    }
}
