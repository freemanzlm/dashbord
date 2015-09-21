package com.ebay.raptor.promotion.interceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ebay.app.raptor.promocommon.service.CSApiService;
import com.ebay.raptor.geo.utils.CountryEnum;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.util.CookieUtil;

public class LanguageInterceptor extends HandlerInterceptorAdapter{

	private String tradLang = "zh_HK/";
	
	//TRUE: User is from HK/TW/MO, use traditional Chinese
	//FALSE: User is from CN, use Simplified Chinese
	private Map<Long, Boolean> langCache = new ConcurrentHashMap<Long, Boolean>();
	
	@Autowired
	private CSApiService service;
	
	@Override
	public void postHandle(HttpServletRequest req,
			HttpServletResponse resp, Object handler, ModelAndView model) throws Exception {
		
		System.err.println("Set page language.");
		UserData user = CookieUtil.getUserDataFromCookie(req);
		Boolean isTradionalLang = langCache.get(user.getUserId());
		if(null != isTradionalLang){
			if(isTradionalLang){
				if(null != model && null != model.getViewName() && -1 == model.getViewName().indexOf(tradLang)){
					model.setViewName(tradLang + model.getViewName());
				}
			}
			return;
		}
		
		//Call the API CS api to get user
		String country = service.getUserCountryByName(user.getUserName());
		if(null != country){
			if(!(CountryEnum.CN.getName()).equals(country)){
				if(null != model && null != model.getViewName() && -1 == model.getViewName().indexOf(tradLang)){
					model.setViewName(tradLang + model.getViewName());
				}
				
				//Cache the user
				langCache.put(user.getUserId(), Boolean.TRUE);
			} else {
				langCache.put(user.getUserId(), Boolean.FALSE);
			}
		}
	}

}
