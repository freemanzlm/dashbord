package com.ebay.raptor.promotion.config;

import com.ebay.raptor.siteApi.EnvironmentUtil;

public class AppCookies {
	// sessionID got by developer site API: GetSessionID
	public final static String EBAY_SESSION_ID_COOKIE_NAME = "eBaySession";
	// token got by developer site API: FetchToken
	public final static String EBAY_TOKEN_COOKIE_NAME = "eBayToken";
	// Which site does user come from: SD, TMP, Backend
	public final static String FROM_SITE_COOKIE_NAME = "fromSite";

	// development back mode
	public final static String HACK_MODE_COOKIE_NAME = "CBTHackMode";

//	public final static String EBAY_CBT_LOGIN_SESSION_ID_COOKIE_NAME = "eBayCBTLoginSession";
	public final static String EBAY_CBT_ADMIN_USER_COOKIE_NAME = "eBayCBTAdminUser";
	public final static String EBAY_CBT_USER_ID_COOKIE_NAME = "eBayCBTUserId";
	public final static String EBAY_CBT_USER_NAME_COOKIE_NAME = "eBayCBTUserName";
	public final static String EBAY_CBT_SESSION_ID_COOKIE_NAME = "eBayCBTSession";
	public final static String EBAY_CBT_LANGUAGE_COOKIE_NAME = "eBayCBTLang";

	public final static String COOKIE_DOMAIN;
	public final static String COOKIE_PATH_ROOT = "/";
	
	static {
		if (EnvironmentUtil.isProduction()) {
			COOKIE_DOMAIN = ".ebay.com.hk"; // expand domain to all CBT app.
		} else {
			COOKIE_DOMAIN = ".ebay.com";
		}
	}
	
}
