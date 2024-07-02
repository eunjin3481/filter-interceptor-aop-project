package org.example.project.config;

import org.example.project.filter.TokenFilter;
import org.example.project.interceptor.DecryptInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenFilter tokenFilter;

    @Autowired
    private DecryptInterceptor decryptInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(decryptInterceptor)
                .addPathPatterns("");



    }

    // 필터 추가
    @Bean
    public FilterRegistrationBean<TokenFilter> loggingFilter() {
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(tokenFilter);
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
}

