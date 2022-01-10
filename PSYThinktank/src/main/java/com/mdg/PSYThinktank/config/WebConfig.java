package com.mdg.PSYThinktank.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${custom.circular-path}")
	private String uploadCircularPath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/media/**").addResourceLocations("file:///" + uploadCircularPath).setCachePeriod(3600)
		.resourceChain(true).addResolver(new PathResourceResolver());
	}
}
