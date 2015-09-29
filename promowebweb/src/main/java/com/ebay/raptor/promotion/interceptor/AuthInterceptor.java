package com.ebay.raptor.promotion.interceptor;

import java.io.IOException;

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
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.service.BaseDataService;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.PromotionUtil;

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
		Cookie [] cookies = request.getCookies();

		if (cookies != null && cookies.length > 0) {
			Long userId = null;
			String sessionId = null;
			boolean userFound = false;
			boolean sessionFound = false;

			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				String cookieValue = cookie.getValue();

				if (CookieUtil.HACKID_COOKIE_KEY.equalsIgnoreCase(cookieName)) {
					// found hack_id which means the access is by hack mode, and no session stored
                    return true;
                }

				if (CookieUtil.USERID_COOKIE_KEY.equalsIgnoreCase(cookieName)) {
					userId = Long.parseLong(cookieValue);
					userFound = true;
				}
				
				if (CookieUtil.SESSIONID_COOKIE_KEY.equalsIgnoreCase(cookieName)) {
					sessionId = cookieValue;
					sessionFound = true;
				}
				
				if (userFound && sessionFound) {
					break;
				}
			}
			
			if (userId != null) {
				String storedSession = dataService.getSdParamterValue(ParameterType.BIZSession,
						CommonConstant.DEFAULT_PARAMETER_STATUS, userId + "");

				if (!StringUtil.isEmpty(sessionId) && sessionId.equalsIgnoreCase(storedSession)) {
					return true;
				}
			}

		}

		return false;
	}

	private void redirectToLogin (HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(PromotionUtil.LOGIN_URL);
		} catch (IOException e) {
			_logger.error(ErrorType.UnableRedirectToUrl, e, PromotionUtil.LOGIN_URL);
		}
	}
}
