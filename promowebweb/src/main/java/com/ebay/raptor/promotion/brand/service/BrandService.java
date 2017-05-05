package com.ebay.raptor.promotion.brand.service;

import java.util.Locale;

import org.springframework.stereotype.Component;

/**
 * 
 * @author lyan2
 */
@Component
public class BrandService {

	/**
	 * Calculate how many brands of a user has passed authentication.
	 * 
	 * @param userId
	 * @return
	 */
	public int countPassedBrandAmount(Long userId) {
		return 2;
	}
	
	public String getBrandIntroduction(Locale locale) {
		return "项目简介";
	}
}
