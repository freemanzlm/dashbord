package com.ebay.raptor.promotion.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestException;
import com.ebay.app.raptor.promocommon.pojo.db.Parameter;
import com.ebay.app.raptor.promocommon.pojo.db.ParameterType;
import com.ebay.app.raptor.promocommon.util.CommonConstant;
import com.ebay.raptor.promotion.config.AppCookies;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.StringUtil;
import com.ebay.raptor.siteApi.util.SiteApiUtil;

@Service
public class LoginService {

    private static CommonLogger logger =
            CommonLogger.getInstance(LoginService.class);

    @Autowired BaseDataService baseDataService;
    @Autowired SiteAPIService siteApiService;
    
    /**
	 * Get user id, user name, language required and whether user is an
	 * administrator.
	 * 
	 * @param request
	 * @return
	 * @throws MissingArgumentException
	 */
	public UserData getUserDataFromCookie(HttpServletRequest request)
			throws MissingArgumentException {
		Cookie [] cookies = request.getCookies();
		
		if (cookies == null || cookies.length <= 0) {
			return null;
		}
		
		Map<String, String> cookieMap = CookieUtil.convertCookieToMap(cookies);
		UserData userData = new UserData();

		String eiasToken = cookieMap.get(AppCookies.EBAY_CBT_USER_ID_COOKIE_NAME);
		try {
			long userId = -1;
			try {
				userId = Long.parseLong(SiteApiUtil.decodeUserId(eiasToken, false));
				userData.setUserId(userId);
			} catch (Exception e) {
				// TODO remove, user id is encoded after DashBoard security branch online
				userId = Long.parseLong(eiasToken);
				userData.setUserId(userId);
			}
		} catch (NumberFormatException e) {
			throw new MissingArgumentException(AppCookies.EBAY_CBT_USER_ID_COOKIE_NAME);
		}

		String userName = cookieMap.get(AppCookies.EBAY_CBT_USER_NAME_COOKIE_NAME);
		if (StringUtil.isEmpty(userName)) {
			throw new MissingArgumentException(
					AppCookies.EBAY_CBT_USER_NAME_COOKIE_NAME);
		}
		userData.setUserName(userName);

		boolean admin = !StringUtil.isEmpty(cookieMap.get(AppCookies.EBAY_CBT_ADMIN_USER_COOKIE_NAME));
		userData.setAdmin(admin);

		return userData;
	}

    public boolean isLoginIPValid (String ip) {
        // load for every check
        List <String> validIps = loadValidIps();

        for (String validIp : validIps) {
            if (validIp.equalsIgnoreCase(ip)) {
                return true;
            }
        }
        logger.error(String.format("Unable to load IP for current accessed user with IP: %s", ip));
        return false;
    }

    private List <String> loadValidIps () {
        List<String> ips = new ArrayList<String>();
        try {
            List<Parameter> params =
            		baseDataService.getSdParameters(ParameterType.BIZBackendIp,
                            CommonConstant.PARAMETER_ENABLE);

            if (params != null && params.size() > 0) {
                Iterator<Parameter> it = params.iterator();

                while (it.hasNext()) {
                    ips.add(it.next().getValue());
                }
            }
        } catch (HttpRequestException e) {
            logger.error("Unable to load the ip list.", e);
        }
        return ips;
    }
}
