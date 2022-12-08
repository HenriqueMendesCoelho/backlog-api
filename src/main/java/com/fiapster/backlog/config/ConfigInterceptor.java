package com.fiapster.backlog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fiapster.backlog.handlers.LoggerInterceptor;

@Configuration
public class ConfigInterceptor implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new LoggerInterceptor());
	}

}
