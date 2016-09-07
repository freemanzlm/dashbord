package com.ebay.raptor.promotion.interceptor;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ebay.app.raptor.cbtcommon.util.CommonConstant;
import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.kernel.util.FastURLEncoder;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.config.AppConfig;
import com.ebay.raptor.promotion.config.AppCookies;
import com.ebay.raptor.promotion.security.BackendTokenData;
import com.ebay.raptor.promotion.security.BackendTokenUtil;
import com.ebay.raptor.promotion.service.BaseDataService;
import com.ebay.raptor.promotion.service.CSApiService;
import com.ebay.raptor.promotion.service.SiteAPIService;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.RequestUtil;
import com.ebay.raptor.siteApi.response.GetTokenStatusResponse.TokenStatus;
import com.ebay.raptor.siteApi.util.SiteApiUtil;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	private static final CommonLogger _logger = CommonLogger.getInstance(AuthInterceptor.class);
	
	@Autowired protected SiteAPIService siteService;
	@Autowired protected BaseDataService dataService;
	@Autowired protected CSApiService csApiService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler != null && handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			HandlerMethod handerMethod = (HandlerMethod)handler;
			AuthNeed annotation = handerMethod.getMethodAnnotation(AuthNeed.class);

			// check the annotated method
			if (annotation != null) {
				Map <String, String> cookieMap = CookieUtil.convertCookieToMap(request.getCookies());
			    boolean success = authenticate(request, response, cookieMap);
			    
                if (!success) {
                	redirectToLogin(request, response);
                    return false;
                }
			}
		}
		
		return true;
	}
	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		// reset session in cookie?
	}
	
	private boolean authenticate (HttpServletRequest request,
			HttpServletResponse response, Map <String, String> cookieMap) {
		/**
		 * 1. Authentication is based on cookie. If there is no cookie, return false. 
		 */
		if (cookieMap == null || cookieMap.size() <=0) return false;
		
		/**
		 * 2. Check if it's visiting from development hack mode.
		 */
		String hackMode = cookieMap.get(AppCookies.HACK_MODE_COOKIE_NAME);
		if (hackMode != null && !hackMode.isEmpty()) {
			return "true".equalsIgnoreCase(hackMode);
		}
		
		/**
		 * 3. Check if user is visiting from back end system or login page.
		 */
		String backendToken = cookieMap.get(AppCookies.BACKEND_TOKEN_COOKIE_NAME);
		String adminUserName = cookieMap.get(AppCookies.EBAY_CBT_ADMIN_USER_COOKIE_NAME);
		
		if (adminUserName != null && !adminUserName.isEmpty() && backendToken != null && !backendToken.isEmpty()) {
			/**
			 * 1. First check if it's visiting from back end system.
			 */
			String ip = request.getRemoteAddr();
			try {
				backendToken = URLDecoder.decode(backendToken, "UTF-8");
				BackendTokenData tokenData = BackendTokenUtil.parse(backendToken, false);
				if (tokenData != null) {
					return BackendTokenUtil.verifyToken(tokenData, adminUserName, ip);
				}
			} catch (Exception e) {
				_logger.error("Token is not parsable.");
			}
		} else {
			/**
			 * 2. If there is eBayToken. It means a user is visiting from eBay login page. So we just verify eBayToken.
			 */
			String eBayToken = (String)cookieMap.get(AppCookies.EBAY_TOKEN_COOKIE_NAME);
			if (eBayToken != null && !eBayToken.isEmpty()) {
				String userId = cookieMap.get(AppCookies.EBAY_CBT_USER_ID_COOKIE_NAME);
				if (userId != null && !userId.isEmpty()) {
					userId = SiteApiUtil.decodeUserId(userId, false);
					return validateEbayToken(eBayToken, userId);
				} else {
					return validateEbayToken(eBayToken, null);
				}
				
			}
		}
		
		return false;
	}

	private void redirectToLogin (HttpServletRequest request, HttpServletResponse response) {
		try {
			String url = AppConfig.LOGIN_URL + '?'
					+ AppConfig.REFER_PARAM + '='
					+ FastURLEncoder.encode(FastURLEncoder.encode(RequestUtil.getFullRequestUrl(request)));
			response.sendRedirect(url);
		} catch (IOException e) {
			_logger.error(ErrorType.UnableRedirectToUrl, e, AppConfig.LOGIN_URL);
		}
	}
	
	/**
	 * Verify whether token is still valid.
	 * @param token Got from cookie, but generated by FetchToken api.
	 * @return
	 */
	private boolean validateEbayToken(String token, String userId) {
		if (token == null || token.isEmpty()) return false;
		
		TokenStatus tokenStatus = siteService.getTokenStatus(token);
		if (tokenStatus != null) {
			if (userId != null && "Active".equalsIgnoreCase(tokenStatus.getStatus())) {
				return userId.equalsIgnoreCase(SiteApiUtil.decodeUserId(tokenStatus.getEIASToken(), true));
			} else {
				return "Active".equalsIgnoreCase(tokenStatus.getStatus());
			}
		} 
			
		return false;
	}
	
}
