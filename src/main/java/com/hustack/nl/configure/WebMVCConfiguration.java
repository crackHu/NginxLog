package com.hustack.nl.configure;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;

@Configuration
public class WebMVCConfiguration {
	
	@Bean
	public HttpMessageConverters customConverters() {
		StringHttpMessageConverter httpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
		return new HttpMessageConverters(httpMessageConverter);
	}
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//				WebMvcConfigurer.super.configureMessageConverters(converters);
//				converters.addAll(customConverters().getConverters());
//			}
//		};
//	}
}