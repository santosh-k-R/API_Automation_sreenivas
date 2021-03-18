package com.cisco.services.api_automation.config;

import com.cisco.services.api_automation.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    public FilterRegistrationBean<AuthFilter> loggingFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/v1/*");

        return registrationBean;
    }
}
