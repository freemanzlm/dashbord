package com.ebay.raptor.promotion.list.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.ViewContext;

public abstract class AbstractListingController {
	public final static String FRAME_OPTIONS_HEADER_KEY = "x-frame-options";
	public final static String DENY_FRAME_OPTIONS = "DENY";
	public final static String SAMEORIGIN_FRAME_OPTIONS = "SAMEORIGIN";
	public final static String ALLOW_FROM_FRAME_OPTIONS = "ALLOW-FROM";

	@Autowired
	PromotionService promoService;
	
	@Autowired 
	ResourceBundleMessageSource messageSource;

	protected void addPromotionContext(ModelAndView model, String promoId, Long userId, String successView, boolean isAdmin) throws PromoException{
		Promotion promotion = promoService.getPromotionById(promoId, userId, isAdmin);
		model.addObject(ViewContext.Promotion.getAttr(), promotion);
		if(null != successView){
			model.setViewName(successView);
		}
	}
	
	protected String resource(String key){
		//TODO Whether need to detect user region for SIMPLE or TRADITIONAL Chinese?
		return messageSource.getMessage(key, null, Locale.SIMPLIFIED_CHINESE);
	}
	
	/**
	 * Accept the agreement after finish upload or confirm listings.
	 * 
	 * @param promoId
	 * @param uid
	 */
	@Deprecated
	protected void acceptAgreement(String promoId, long uid){
//		promoService.acceptAgreement(promoId, uid);
	}
	
}
