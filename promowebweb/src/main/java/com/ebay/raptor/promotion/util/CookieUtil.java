package com.ebay.raptor.promotion.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.util.CommonConstant;
import com.ebay.app.raptor.promocommon.util.CommonUtil;
import com.ebay.app.raptor.promocommon.util.StringUtil;
import com.ebay.raptor.promotion.pojo.UserData;

public class CookieUtil {
    // *Start--Note: used the same cookie as bizreport, in order
    //               to support switch between 2 APPs without re-login.
	public final static String USERNAME_COOKIE_KEY = "eBayCBTUserName";
	public final static String USERID_COOKIE_KEY = "eBayCBTUserId";
	public final static String HACKID_COOKIE_KEY = "eBayBizHackId";
	public final static String SESSIONID_COOKIE_KEY = "eBayCBTSession";
	public final static String ADMIN_COOKIE_NAME = "eBayCBTAdmin";
	public final static String LANG_COOKIE_NAME = "eBayCBTLang";
	
	public final static String EBAY_CBT_ADMIN_USER_COOKIE_NAME = "eBayCBTAdminUser";
	public final static String EBAY_CBT_USER_ID_COOKIE_NAME = "eBayCBTUserId";
	public final static String EBAY_CBT_USER_NAME_COOKIE_NAME = "eBayCBTUserName";
	public final static String EBAY_CBT_SESSION_ID_COOKIE_NAME = "eBayCBTSession";
	public final static String EBAY_CBT_LANGUAGE_COOKIE_NAME = "eBayCBTLang";
	
	public final static String EBAY_CBT_LOGIN_SESSION_COOKIE_NAME = "eBayCBTLoginSession";
	public final static String EBAY_CBT_REFERURL_COOKIE_NAME = "eBayCBTRedirectUrl";
	
	public final static String EBAY_TMP_ADMIN_USER_COOKIE_NAME = "eBayTMPAdminUser";
	public final static String EBAY_TMP_MAIN_ACCOUNT_COOKIE_NAME = "eBayTmpMainAccount";
	public final static String EBAY_TMP_SUB_ACCOUNT_COOKIE_NAME = "eBayTmpSubAccount";
	public final static String EBAY_TMP_SUB_ACCOUNT_ID_COOKIE_NAME = "eBayTmpSubAccountId";
	public final static String EBAY_TMP_SESSION_ID_COOKIE_NAME = "eBayTmpSession";
	public final static String EBAY_TMP_LANGUAGE_COOKIE_NAME = "eBayTMPLang";
	
	public final static String EBAY_TMP_LOGIN_SESSION_COOKIE_NAME = "eBayTMPLoginSession";
	public final static String EBAY_TMP_REFERURL_COOKIE_NAME = "eBayTMPRedirectUrl";

	public final static int ONE_DAY_COOKIE_LIFESPAN = 3600 * 24; // 1 Day
	public final static int TEN_MIN_COOKIE_LIFESPAN = 600; // 10 min
	// *End--Note
	
	public final static int COOKIE_LIFESPAN = 3600 * 24; // 24h
	
	public final static String COOKIE_DOMAIN;
	public final static String COOKIE_PATH_ROOT = "/"; // TODO - set the path

	static {
		if (CommonUtil.isProduction()) {
			COOKIE_DOMAIN = ".ebay.com.hk"; // expand domain to all CBT app.
		} else {
			COOKIE_DOMAIN = null;
		}
	}
	
	public static void setCookie (HttpServletResponse response, String cookieName,
			String cookieVal, int maxAge, String path, String domain, boolean secure) {
		Cookie cookie = new Cookie (cookieName, cookieVal);
		cookie.setMaxAge(maxAge); // <0 never store; =0 delete; >0 store
		cookie.setPath(path);

		if (!StringUtil.isEmpty(domain)) {
			cookie.setDomain(domain);
		}

		if (secure) {
			cookie.setSecure(secure);
		}

		response.addCookie(cookie);
	}
	
	public static void setCBTCookie (HttpServletResponse response,
			String cookieName, String cookieVal, String path, String domain) {
		setCookie(response, cookieName,
				cookieVal, COOKIE_LIFESPAN, path, domain, false);
	}
	
	public static void setCBTPromotionCookie (HttpServletResponse response,
			String cookieName, String cookieVal) {
		setCookie(response, cookieName, cookieVal,
				COOKIE_LIFESPAN, COOKIE_PATH_ROOT, COOKIE_DOMAIN, false);
	}

	public static UserData getUserDataFromCookieOverrideLang (
	        HttpServletRequest request) throws MissingArgumentException {
		UserData user = getUserDataFromCookie(request);
		String paramLang = request.getParameter(PromotionUtil.LANG_REQUEST_PARAMETER_KEY);

		if (!StringUtil.isEmpty(paramLang)) {
			user.setLang(paramLang);
		}

		return user;
	}
	
	public static UserData getUserDataFromCookie (
	        HttpServletRequest request) throws MissingArgumentException {
		Map <String, String> cookieMap =  convertCookieToMap(request.getCookies());
		
		long userId = -1;

		try {
			userId = Long.parseLong(cookieMap.get(EBAY_CBT_USER_ID_COOKIE_NAME));
		} catch (NumberFormatException e) {
			throw new MissingArgumentException(EBAY_CBT_USER_ID_COOKIE_NAME);
		}
		
		String userName = cookieMap.get(EBAY_CBT_USER_NAME_COOKIE_NAME);
		
		if (StringUtil.isEmpty(userName)) {
			throw new MissingArgumentException(EBAY_CBT_USER_NAME_COOKIE_NAME);
		}
		
		String lang = cookieMap.get(EBAY_CBT_LANGUAGE_COOKIE_NAME);
		
		if (StringUtil.isEmpty(lang)) {
			lang = CommonConstant.ZHCN_LANGUAGE;
		}
		
		boolean admin = !StringUtil.isEmpty(cookieMap.get(EBAY_CBT_ADMIN_USER_COOKIE_NAME));
		
		return new UserData(userId, userName, admin, lang);
	}

	public static Map <String, String> convertCookieToMap (Cookie [] cookies) {
		Map <String, String> cookieMap = new HashMap <String, String> ();

		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				cookieMap.put(c.getName(), c.getValue());
			}
		}

		return cookieMap;
	}
}
