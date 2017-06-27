package com.ebay.raptor.promotion.config;

import java.util.Properties;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.ebay.app.raptor.promocommon.concurrent.CommonExecutorService;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestService;
import com.ebay.raptor.promotion.service.CSApiService;
import com.ebay.raptor.promotion.validation.LocalValidatorFactoryBean;

@Configuration
public class PromoConfiguration {
	@Bean(name="messageSource")
	public ResourceBundleMessageSource getResourceBundleMessageSource () {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource ();
		source.setBasenames("Message", "ExcelHeader", "Errors");
		return source;
	}
	
	@Bean(name="validator")
	public LocalValidatorFactoryBean getSpringValidatorAdapter () {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setProviderClass(HibernateValidator.class);
		validator.setValidationMessageSource(getResourceBundleMessageSource());
		Properties properties = new Properties();
		properties.put("hibernate.validator.fail_fast", "true");
		validator.setValidationProperties(properties);
		return validator;
	}
	
	@Bean
	public HttpRequestService getHttpRequestService () {
		return new HttpRequestService();
	}
	
	@Bean
	public CSApiService getCSApiService () {
	    return new CSApiService();
	}
	
	@Bean
	public CommonExecutorService getExecService(){
		return new CommonExecutorService();
	}
	
	@Bean
	public com.ebay.app.raptor.cbtcommon.concurrent.CommonExecutorService getCommonExecService(){
		return new com.ebay.app.raptor.cbtcommon.concurrent.CommonExecutorService();
	}
	
	@Bean
	public com.ebay.app.raptor.cbtcommon.httpRequest.HttpRequestService getCommonHttpRequestService () {
		return new com.ebay.app.raptor.cbtcommon.httpRequest.HttpRequestService();
	}
}
