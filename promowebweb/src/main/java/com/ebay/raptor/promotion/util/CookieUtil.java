package com.ebay.raptor.promotion.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
	
	public static UserData getUserDataFromCookie (
	        HttpServletRequest request) throws MissingArgumentException {
		long userid = -1;
		String userName = "";
		boolean admin = false;
		Cookie [] cookies = request.getCookies();

		if (cookies != null && cookies.length > 0) {
			boolean idFound = false;
			boolean nameFound = false;
			boolean adminFound = false;

			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();

				if (CookieUtil.USERID_COOKIE_KEY.equalsIgnoreCase(cookieName)) {
					try {
						userid = Long.parseLong(cookie.getValue());
						idFound = true;
					} catch (NumberFormatException e) {
						// handle later
					}
				}

				if (CookieUtil.USERNAME_COOKIE_KEY.equalsIgnoreCase(cookieName)) {
					userName = cookie.getValue();
					nameFound = true;
				}
				
				if (CookieUtil.ADMIN_COOKIE_NAME.equalsIgnoreCase(cookieName)) {
				    admin = Boolean.parseBoolean(cookie.getValue());
				    adminFound = true;
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

		UserData user = new UserData(userid, userName, admin);

		return user;
	}
}
