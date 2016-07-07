package com.ebay.raptor.promotion.locale;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 
 * @author lyan2
 */
public class LocaleUtil {
	/**
	 * Get locale string format like: en_US, zh_CN.
	 * @param locale
	 * @return
	 */
	public static String getLocale(Locale locale) {
//		return locale.getLanguage() + "_" + locale.getCountry();
		return locale.getCountry();
	}
	
	/**
	 * Get current Locale of user request.
	 *  
	 * @return Locale
	 */
	public static Locale getCurrentLocale() {
		// in spring, use this code
		return LocaleContextHolder.getLocale();
	}
}
