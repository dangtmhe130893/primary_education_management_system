package com.primary_education_system.config;

import com.primary_education_system.exception.GlobalExceptionHandlerFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistration {

	@Bean
	public FilterRegistrationBean registrationExceptionFiler(GlobalExceptionHandlerFilter filter) {
		FilterRegistrationBean reg = new FilterRegistrationBean(filter);
		reg.setOrder(1);
		return reg;

	}
}
