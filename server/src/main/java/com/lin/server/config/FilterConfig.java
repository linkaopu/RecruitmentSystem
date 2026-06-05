package com.lin.server.config;

import com.lin.server.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置类
 * 注册自定义过滤器
 */
@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 注册JWT认证过滤器
     * 设置过滤器的顺序，确保在CORS过滤器之后执行
     */
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilterRegistration() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtAuthenticationFilter);

        // 设置过滤器名称
        registrationBean.setName("jwtAuthenticationFilter");

        // 设置过滤器匹配的URL模式
        registrationBean.addUrlPatterns("/*");

        // 设置过滤器顺序，数字越小越先执行
        // 设置为2，确保在CORS过滤器（默认order为0）之后执行
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
