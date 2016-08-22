package com.ebay.raptor.promotion.config;

import com.ebay.app.raptor.promocommon.util.CommonUtil;

public class AppCookies {
	// *Start--Note: used the same cookie as bizreport, in order
    //               to support switch between 2 APPs without re-login.
	public final static String HACKID_COOKIE_KEY = "eBayBizHackId";
	public final static String ADMIN_COOKIE_NAME = "eBayCBTAdmin";
	
	public final static String EBAY_CBT_ADMIN_USER_COOKIE_NAME = "eBayCBTAdminUser";
	public final static String EBAY_CBT_USER_ID_COOKIE_NAME = "eBayCBTUserId";
	public final static String EBAY_CBT_PROMOTION_USER_ID_COOKIE_NAME = "eBayCBTPromoUserId";
	public final static String EBAY_CBT_USER_NAME_COOKIE_NAME = "eBayCBTUserName";
	public final static String EBAY_CBT_SESSION_ID_COOKIE_NAME = "eBayCBTSession";
	public final static String EBAY_CBT_LANGUAGE_COOKIE_NAME = "eBayCBTLang";
	
	public final static String COOKIE_DOMAIN; 
	public final static String COOKIE_PATH_ROOT = "/";
	
	static {
		if (CommonUtil.isProduction()) {
			COOKIE_DOMAIN = ".ebay.com.hk"; // expand domain to all CBT app.
		} else {
			COOKIE_DOMAIN = null;
		}
	}
}
