package com.ebay.raptor.promotion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.ebay.app.raptor.promocommon.concurrent.CommonExecutorService;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestService;
import com.ebay.app.raptor.promocommon.service.CSApiService;

@Configuration
public class PromoConfiguration {
	@Bean
	public ResourceBundleMessageSource getResourceBundleMessageSource () {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource ();
		source.setBasenames("Message");
		return source;
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
}
