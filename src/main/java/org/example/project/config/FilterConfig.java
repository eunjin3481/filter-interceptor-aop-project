package org.example.project.config;

import org.example.project.filter.DecryptionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<DecryptionFilter> filter() {
        FilterRegistrationBean<DecryptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new DecryptionFilter());
        registrationBean.addUrlPatterns("/user/*");
        registrationBean.setName("filter");
        return registrationBean;
    }
}

