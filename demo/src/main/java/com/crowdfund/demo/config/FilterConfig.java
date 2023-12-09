package com.crowdfund.demo.config;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ApiKeyValidationFilter> apiKeyValidationFilter() {
        FilterRegistrationBean<ApiKeyValidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiKeyValidationFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Set the order explicitly
        return registrationBean;
    }
}

