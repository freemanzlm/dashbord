package com.ebay.raptor.promotion.util;

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
		return locale.getLanguage() + "_" + locale.getCountry();
	}
	
	/**
	 * Get locale from like: CN, TW, HK.
	 * @param region
	 * @return locale
	 */
	public static Locale getLocale(String region) {
		if(region.equalsIgnoreCase("CN")) {
			return new Locale("zh","CN");
		} else if(region.equalsIgnoreCase("HK")) {
			return new Locale("zh", "HK");
		} else if(region.equalsIgnoreCase("TW")) {
			return new Locale("zh", "TW");
		}
		return LocaleContextHolder.getLocale();
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
