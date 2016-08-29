package com.ebay.raptor.promotion.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.ebay.raptor.promotion.config.AppCookies;

public class CookieUtil {

	public final static int ONE_DAY_COOKIE_LIFESPAN = 3600 * 24; // 1 Day
	public final static int TEN_MIN_COOKIE_LIFESPAN = 600; // 10 min
	public final static int SESSION_COOKIE_LIFESPAN = -1; // session cookie
	
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
	
	public static void setOneDayCookie(HttpServletResponse response,
			String cookieName, String cookieVal, String path, String domain) {
		setCookie(response, cookieName, cookieVal, ONE_DAY_COOKIE_LIFESPAN,
				path, domain, false);
	}
	
	public static void setCBTCookie(HttpServletResponse response,
			String cookieName, String cookieVal, int maxAge) {
		setCookie(response, cookieName, cookieVal, maxAge,
				AppCookies.COOKIE_PATH_ROOT, AppCookies.COOKIE_DOMAIN, false);
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
