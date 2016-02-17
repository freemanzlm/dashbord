package com.ebay.raptor.promotion.validation;

import javax.validation.MessageInterpolator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.MessageSource;

public class LocalValidatorFactoryBean extends org.springframework.validation.beanvalidation.LocalValidatorFactoryBean {

	private static class HibernateValidatorDelegate {
		public static MessageInterpolator buildMessageInterpolator(MessageSource messageSource) {
			return new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(messageSource));
		}
	}

	@Override
	public void setValidationMessageSource(MessageSource messageSource) {
		setMessageInterpolator(HibernateValidatorDelegate.buildMessageInterpolator(messageSource));
	}
}
