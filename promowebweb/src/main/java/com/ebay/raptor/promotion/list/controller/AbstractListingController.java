package com.ebay.raptor.promotion.list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.ebay.raptor.promotion.promo.service.PromotionService;

/**
 * 
 * @author lyan2
 */
public abstract class AbstractListingController {

	@Autowired
	PromotionService promoService;
	
	@Autowired 
	ResourceBundleMessageSource messageSource;

	/**
	 * Get messages from resource bundle. Locale is got from LocaleContextHolder. This means locale may be determined by ACCEPT header.
	 * 
	 * @param key
	 * @return
	 */
	protected String getMessage(String key){
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}
	
	/**
	 * Accept the agreement before enroll or confirm.
	 * However, requirement wants user check legal terms every time, so, this method is useless. That's why it does nothing now.
	 * 
	 * @param promoId
	 * @param uid
	 */
	protected void acceptAgreement(String promoId, long uid){
//		promoService.acceptAgreement(promoId, uid);
	}
	
}
