package com.ebay.raptor.promotion.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.app.raptor.promocommon.pojo.db.ParameterType;
import com.ebay.app.raptor.promocommon.util.CommonConstant;
import com.ebay.app.raptor.promocommon.util.StringUtil;
import com.ebay.kernel.util.FastURLEncoder;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.service.BaseDataService;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.PromotionUtil;
import com.ebay.raptor.promotion.util.RequestUtil;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	private static final CommonLogger _logger = CommonLogger.getInstance(AuthInterceptor.class);
	
	@Autowired protected BaseDataService dataService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler != null && handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			HandlerMethod handerMethod = (HandlerMethod)handler;
			AuthNeed annotation = handerMethod.getMethodAnnotation(AuthNeed.class);

			// check the annotated method
			if (annotation != null) {
			    boolean success = authenticate(request, response);
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
	
	private boolean authenticate (HttpServletRequest request, HttpServletResponse response) {
		Map <String, String> cookieMap = CookieUtil.convertCookieToMap(request.getCookies());
		String userName = null;
		String sessionId = null;
		ParameterType type = null;

		if (cookieMap != null && cookieMap.size() > 0) {
			if (!StringUtil.isEmpty(cookieMap.get(CookieUtil.HACKID_COOKIE_KEY))) {
				return true;
			} else if (!StringUtil.isEmpty(cookieMap.get(CookieUtil.EBAY_CBT_ADMIN_USER_COOKIE_NAME))) {
				userName = cookieMap.get(CookieUtil.EBAY_CBT_ADMIN_USER_COOKIE_NAME);
				sessionId = cookieMap.get(CookieUtil.EBAY_CBT_SESSION_ID_COOKIE_NAME);
				type = ParameterType.BackendSession;
			} else {
				userName = cookieMap.get(CookieUtil.EBAY_CBT_USER_NAME_COOKIE_NAME);
				sessionId = cookieMap.get(CookieUtil.EBAY_CBT_SESSION_ID_COOKIE_NAME);
				type = ParameterType.DashboardSession;
			}
			
			if (userName != null) {
				String storedSession = dataService.getSdParamterValue(type,
						CommonConstant.PARAMETER_ENABLE, userName);

				if (!StringUtil.isEmpty(sessionId) && sessionId.equalsIgnoreCase(storedSession)) {
					return true;
				}
			}
		}

		return false;
	}

	private void redirectToLogin (HttpServletRequest request, HttpServletResponse response) {
		try {
			String url = PromotionUtil.LOGIN_URL + '?'
					+ PromotionUtil.REFER_PARAM + '='
					+ FastURLEncoder.encode(RequestUtil.getFullRequestUrl(request));
			_logger.log("redirect to url: " + url);
			response.sendRedirect(url);
		} catch (IOException e) {
			_logger.error(ErrorType.UnableRedirectToUrl, e, PromotionUtil.LOGIN_URL);
		}
	}
}
