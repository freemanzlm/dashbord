package com.ebay.raptor.promotion.interceptor;

import java.io.IOException;
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
import com.ebay.app.raptor.promocommon.pojo.db.ParameterType;
import com.ebay.kernel.util.FastURLEncoder;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.config.AppCookies;
import com.ebay.raptor.promotion.service.BaseDataService;
import com.ebay.raptor.promotion.service.CSApiService;
import com.ebay.raptor.promotion.service.SiteAPIService;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.PromotionUtil;
import com.ebay.raptor.promotion.util.RequestUtil;
import com.ebay.raptor.promotion.util.StringUtil;
import com.ebay.raptor.siteApi.response.GetTokenStatusResponse.TokenStatus;

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
                	if (StringUtil.isEmpty(cookieMap.get(AppCookies.EBAY_CBT_ADMIN_USER_COOKIE_NAME))) {
                		redirectToLogin(request, response);
                	} else {
                		try {
                			response.sendRedirect("error");
                		} catch (IOException e) {
                			_logger.error(ErrorType.UnableRedirectToUrl, e, "error");
                		}
                	}
                    
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
		 * 3. If it is not in hack mode. Then check if it's administrator is visiting from back end system.
		 * If user is visiting from back end system, it will have admin user name and session id in database.
		 * This is just for back compatibility.
		 */
		String sessionId = cookieMap.get(AppCookies.EBAY_CBT_SESSION_ID_COOKIE_NAME);
//		sessionId = "0i8AAA**d5fa2c2e1560a7802cb15fc1ffff5f7b465a1ddd-d0a6-4caa-ad59-b7f558f0654e";
		sessionId = (sessionId == null || sessionId.isEmpty()) ? cookieMap.get(AppCookies.EBAY_SESSION_ID_COOKIE_NAME) : sessionId;
		if (sessionId != null && !sessionId.isEmpty()) {
			String userName = null;
			ParameterType type = null;
			
			String adminUserName = cookieMap.get(AppCookies.EBAY_CBT_ADMIN_USER_COOKIE_NAME);
			if (!StringUtil.isEmpty(adminUserName)) {
				userName = adminUserName;
				type = ParameterType.BackendSession;
			} else {
				/*
				 * TODO, remove this back compatibility when DashBoard security branch is online.
				 * Only back end system need to check session in the future. 
				 */
				userName = cookieMap.get(AppCookies.EBAY_CBT_USER_NAME_COOKIE_NAME);
				type = ParameterType.DashboardSession;
			}
			String storedSession = dataService.getSdParamterValue(type, CommonConstant.PARAMETER_ENABLE, userName);
			if (sessionId != null && sessionId.equalsIgnoreCase(storedSession)) {
				return true;
			}
		}
		
		/**
		 * 4. If there is eBayToken (eBayToken and eBaySession are always existed at the same time).
		 * It means a user is visiting from eBay login page. So we just verify eBayToken.
		 */
		String eBayToken = cookieMap.get(AppCookies.EBAY_TOKEN_COOKIE_NAME);
		
		return validateEbayToken(eBayToken);
	}

	private void redirectToLogin (HttpServletRequest request, HttpServletResponse response) {
		try {
			String url = PromotionUtil.LOGIN_URL + '?'
					+ PromotionUtil.REFER_PARAM + '='
					+ FastURLEncoder.encode(FastURLEncoder.encode(RequestUtil.getFullRequestUrl(request)));
			response.sendRedirect(url);
		} catch (IOException e) {
			_logger.error(ErrorType.UnableRedirectToUrl, e, PromotionUtil.LOGIN_URL);
		}
	}
	
	/**
	 * Verify whether token is still valid.
	 * @param token Got from cookie, but generated by FetchToken api.
	 * @return
	 */
	private boolean validateEbayToken(String token) {
		if (token == null || token.isEmpty()) return false;
		
		TokenStatus tokenStatus = siteService.getTokenStatus(token);
		if (tokenStatus != null) {
			// Token is expired now.
			return "Active".equalsIgnoreCase(tokenStatus.getStatus());
		} else {
			// Token is expired and removed from remote service.
			return false;
		}
	}
}
