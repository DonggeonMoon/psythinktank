package com.mdg.PSYThinktank.config;


import com.mdg.PSYThinktank.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/stockList")
                .addPathPatterns("/boardList")
                .addPathPatterns("/stock")
                .addPathPatterns("/board");
    }
}
	