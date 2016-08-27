package com.ebay.raptor.promotion.config;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.ebay.app.raptor.cbtcommon.util.EnviromentUtil;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.service.SiteAPIService;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.StringUtil;
import com.ebay.raptor.siteApi.response.GetUserResponse;
import com.ebay.raptor.siteApi.util.SiteApiUtil;

/**
 * 
 * @author lyan2
 */
public class AppCookies {
	// sessionID got by developer site API: GetSessionID
	public final static String EBAY_SESSION_ID_COOKIE_NAME = "eBaySession";
	// token got by developer site API: FetchToken
	public final static String EBAY_TOKEN_COOKIE_NAME = "eBayToken";
	// Which site does user come from: SD, TMP, Backend
	public final static String FROM_SITE_COOKIE_NAME = "fromSite";

	// development back mode
	public final static String HACK_MODE_COOKIE_NAME = "CBTHackMode";

	public final static String EBAY_CBT_ADMIN_USER_COOKIE_NAME = "eBayCBTAdminUser";
	public final static String EBAY_CBT_USER_ID_COOKIE_NAME = "eBayCBTUserId";
	public final static String EBAY_CBT_USER_NAME_COOKIE_NAME = "eBayCBTUserName";
	public final static String EBAY_CBT_SESSION_ID_COOKIE_NAME = "eBayCBTSession";
	public final static String EBAY_CBT_LANGUAGE_COOKIE_NAME = "eBayCBTLang";

	public final static String COOKIE_DOMAIN;
	public final static String COOKIE_PATH_ROOT = "/";

	private static SiteAPIService siteApiService;

	static {
		if (EnviromentUtil.isProduction()) {
			COOKIE_DOMAIN = ".ebay.com.hk"; // expand domain to all CBT app.
		} else {
			COOKIE_DOMAIN = null;
		}
	}

	/**
	 * Get user id, user name, language required and whether user is an
	 * administrator.
	 * 
	 * @param request
	 * @return
	 * @throws MissingArgumentException
	 */
	public static UserData getUserDataFromCookie(HttpServletRequest request)
			throws MissingArgumentException {
		Cookie [] cookies = request.getCookies();
		
		if (cookies == null || cookies.length <= 0) {
			return null;
		}
		
		Map<String, String> cookieMap = CookieUtil.convertCookieToMap(cookies);
		UserData userData = new UserData();

		String eBayToken = (String) cookieMap.get(AppCookies.EBAY_TOKEN_COOKIE_NAME);
		if (eBayToken != null && !eBayToken.isEmpty()) {
			// if there is eBay token, it means user is visiting from eBay login site.
			GetUserResponse.User user = getSiteApiService().getUser(eBayToken);
			userData.setUserName(user.getUserName());
			userData.setUserId(Long.parseLong(SiteApiUtil.decodeUserId(user.getEiasToken(), true)));
		} else {
			
			String eiasToken = cookieMap.get(EBAY_CBT_USER_ID_COOKIE_NAME);
			try {
				long userId = -1;
				try {
					userId = Long.parseLong(SiteApiUtil.decodeUserId(eiasToken, false));
					userData.setUserId(userId);
				} catch (NumberFormatException e) {
					// TODO remove, user id is encoded after DashBoard security branch online
					userId = Long.parseLong(eiasToken);
					userData.setUserId(userId);
				}
			} catch (NumberFormatException e) {
				throw new MissingArgumentException(EBAY_CBT_USER_ID_COOKIE_NAME);
			}

			String userName = cookieMap.get(AppCookies.EBAY_CBT_USER_NAME_COOKIE_NAME);
			if (StringUtil.isEmpty(userName)) {
				throw new MissingArgumentException(
						AppCookies.EBAY_CBT_USER_NAME_COOKIE_NAME);
			}
			userData.setUserName(userName);
		}

		boolean admin = !StringUtil.isEmpty(cookieMap.get(AppCookies.EBAY_CBT_ADMIN_USER_COOKIE_NAME));
		userData.setAdmin(admin);

		return userData;
	}

	public static SiteAPIService getSiteApiService() {
		if (siteApiService == null) {
			siteApiService = new SiteAPIService();
		}
		return siteApiService;
	}

	public static void setSiteApiService(SiteAPIService siteApiService) {
		AppCookies.siteApiService = siteApiService;
	}
}
