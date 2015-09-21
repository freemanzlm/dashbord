package com.ebay.raptor.promotion.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.util.CommonUtil;
import com.ebay.app.raptor.promocommon.util.StringUtil;
import com.ebay.raptor.promotion.pojo.UserData;

public class CookieUtil {
    // *Start--Note: used the same cookie as bizreport, in order
    //               to support switch between 2 APPs without re-login.
	public final static String USERNAME_COOKIE_KEY = "eBayBizUserName";
	public final static String USERID_COOKIE_KEY = "eBayBizUserId";
	public final static String HACKID_COOKIE_KEY = "eBayBizHackId";
	public final static String SESSIONID_COOKIE_KEY = "eBayBizSession";
	public final static String ADMIN_COOKIE_NAME = "eBayCBTAdmin";
	public final static String LANG_COOKIE_NAME = "eBayCBTLang";
	// *End--Note
	
	public final static int COOKIE_LIFESPAN = 3600 * 24; // 24h
	
	public final static String COOKIE_DOMAIN;
	public final static String COOKIE_PATH_ROOT = "/promotion"; // TODO - set the path

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
	
	public static UserData getUserDataFromCookie (
	        HttpServletRequest request) throws MissingArgumentException {
		long userid = -1;
		String userName = "";
		boolean admin = false;
		String lang = "";
		Cookie [] cookies = request.getCookies();

		if (cookies != null && cookies.length > 0) {
			boolean idFound = false;
			boolean nameFound = false;
			boolean adminFound = false;

			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				String cookieVal = cookie.getValue();

				if (CookieUtil.USERID_COOKIE_KEY.equalsIgnoreCase(cookieName)) {
					try {
						userid = Long.parseLong(cookieVal);
						idFound = true;
					} catch (NumberFormatException e) {
						// handle later
					}
				}

				if (CookieUtil.USERNAME_COOKIE_KEY.equalsIgnoreCase(cookieName)) {
					userName = cookieVal;
					nameFound = true;
				}
				
				if (CookieUtil.ADMIN_COOKIE_NAME.equalsIgnoreCase(cookieName)) {
				    admin = Boolean.parseBoolean(cookieVal);
				    adminFound = true;
                }
				
				if (CookieUtil.LANG_COOKIE_NAME.equalsIgnoreCase(cookieName)) {
					lang = cookieVal;
                }

				if (idFound && nameFound && adminFound) {
					break;
				}
			}
		}

		List <String> missedArguments = new ArrayList<String>();
		if (userid == -1) {
			missedArguments.add(CookieUtil.USERID_COOKIE_KEY);
		}
		if (StringUtil.isEmpty(userName)) {
			missedArguments.add(CookieUtil.USERNAME_COOKIE_KEY);
		}
		if (missedArguments.size() > 0) {
			throw new MissingArgumentException(missedArguments.toArray());
		}

		UserData user = new UserData(userid, userName, admin, lang);

		return user;
	}
}
