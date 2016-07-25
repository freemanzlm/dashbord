package com.ebay.raptor.promotion.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ebay.app.raptor.cbtcommon.util.CommonConstant;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.raptor.promotion.config.AppCookies;
import com.ebay.raptor.promotion.pojo.UserData;

public class CookieUtil {
    // *Start--Note: used the same cookie as bizreport, in order
    //               to support switch between 2 APPs without re-login.

	public final static int ONE_DAY_COOKIE_LIFESPAN = 3600 * 24; // 1 Day
	public final static int TEN_MIN_COOKIE_LIFESPAN = 600; // 10 min
	
	/**
	 * Set a new cookie in http reponse.
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge, <0 session cookie; =0 delete; >0 seconds
	 * @param path
	 * @param domain
	 * @param secure
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge, String path, String domain, boolean secure) {
		if (name == null || name.isEmpty()) return;
		
		Cookie cookie = new Cookie (name, value);
		cookie.setMaxAge(maxAge); // <0 never store; =0 delete; >0 store
		cookie.setPath(path);

		if (domain != null && !domain.isEmpty()) {
			cookie.setDomain(domain);
		}

		cookie.setSecure(secure);
		response.addCookie(cookie);
	}
	
	/**
	 * Set a new cookie in HTTP response, secure is false by default.
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge, <0 session cookie; =0 delete; >0 seconds
	 * @param path
	 * @param domain
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge, String path, String domain) {
		setCookie(response, name, value, maxAge, path, domain, false);
	}
	
	/**
	 * Set a new cookie with default path and domain.
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		if (name != null && !name.isEmpty()) {
			Cookie cookie = new Cookie (name, value);
			response.addCookie(cookie);
		}
	}
	
	public static void setCBTCookie (HttpServletResponse response,
			String cookieName, String cookieVal, String path, String domain) {
		setCookie(response, cookieName,
				cookieVal, ONE_DAY_COOKIE_LIFESPAN, path, domain, false);
	}
	
	public static void setCBTPromotionCookie (HttpServletResponse response,
			String cookieName, String cookieVal) {
		setCookie(response, cookieName, cookieVal,
				ONE_DAY_COOKIE_LIFESPAN, AppCookies.COOKIE_PATH_ROOT, AppCookies.COOKIE_DOMAIN, false);
	}

	/**
	 * Get user id, user name, language required and whether user is an administrator.
	 * @param request
	 * @return
	 * @throws MissingArgumentException
	 */
	public static UserData getUserDataFromCookie (
	        HttpServletRequest request) throws MissingArgumentException {
		Map <String, String> cookieMap =  convertCookieToMap(request.getCookies());
		
		// Get user data from token first, this can prevent people from changing cookie value to visit our site.
		String token = cookieMap.get(AppCookies.EBAY_CBT_TOKEN_COOKIE_NAME);
		if (token != null) {
			try {
				TokenData tokenData = TokenUtil.parse(token, true);
				return new UserData(tokenData.getUserId(), tokenData.getUserName(), tokenData.getIsAdmin(), tokenData.getLanguage());
			} catch (Exception e) {
				throw new MissingArgumentException(AppCookies.EBAY_CBT_TOKEN_COOKIE_NAME);
			}
		}

		// TODO
		// After Dashboard and Bizreport have implement the same token mechanism, remove below code.
		System.out.println("userID: get from non token!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		long userId = -1;
		try {
			userId = Long.parseLong(cookieMap.get(AppCookies.EBAY_CBT_USER_ID_COOKIE_NAME));
		} catch (NumberFormatException e) {
			throw new MissingArgumentException(AppCookies.EBAY_CBT_USER_ID_COOKIE_NAME);
		}
		
		String userName = cookieMap.get(AppCookies.EBAY_CBT_USER_NAME_COOKIE_NAME);
		
		if (StringUtil.isEmpty(userName)) {
			throw new MissingArgumentException(AppCookies.EBAY_CBT_USER_NAME_COOKIE_NAME);
		}
		
		String lang = cookieMap.get(AppCookies.EBAY_CBT_LANGUAGE_COOKIE_NAME);
		
		if (StringUtil.isEmpty(lang)) {
			lang = CommonConstant.ZHCN_LANGUAGE;
		}
		
		boolean admin = !StringUtil.isEmpty(cookieMap.get(AppCookies.EBAY_CBT_ADMIN_USER_COOKIE_NAME));
		
		return new UserData(userId, userName, admin, lang);
	}

	/**
	 * Store cookies' name and value in a HashMap object. The same name cookie would be overridden.
	 * @param cookies
	 * @return
	 */
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
