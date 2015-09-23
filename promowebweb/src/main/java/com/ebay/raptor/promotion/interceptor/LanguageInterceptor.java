package com.ebay.raptor.promotion.interceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ebay.app.raptor.promocommon.service.CSApiService;
import com.ebay.app.raptor.promocommon.util.CommonConstant;
import com.ebay.app.raptor.promocommon.util.EnviromentUtil;
import com.ebay.app.raptor.promocommon.util.trans.ZHConverter;
import com.ebay.raptor.geo.utils.CountryEnum;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.StringUtil;

public class LanguageInterceptor extends HandlerInterceptorAdapter{

	private String tradLang = "zh_HK/";
	
	private String lang = "lang";
	
	//TRUE: User is from HK/TW/MO, use traditional Chinese
	//FALSE: User is from CN, use Simplified Chinese
	private Map<Long, Boolean> langCache = new ConcurrentHashMap<Long, Boolean>();
	
	private Map<Long, String> regionCache = new ConcurrentHashMap<Long, String>(); 
	
	@Autowired
	private CSApiService service;
	
	@Override
	public void postHandle(HttpServletRequest req,
			HttpServletResponse resp, Object handler, ModelAndView model) throws Exception {
		//TODO timeout issue.
		boolean isTimeout = false;
		if(isTimeout){
			return;
		}
		
		//Call the API CS api to get user
		UserData user = CookieUtil.getUserDataFromCookie(req);
		String region = "CN";
		try{
			region = getRegionFromCacheOrAPI(model, user.getUserId(), user.getUserName());
		} catch(Throwable e){
			isTimeout = true;
		}
		
		System.err.println("Set page language.");
		//Check lang param, if yes then take as first priority
		String langParam = (null != req.getParameter(lang)) ? req.getParameter(lang).toString() : "";
		if(!StringUtil.isEmpty(langParam)){
			if(CommonConstant.ZHHK_LANGUAGE.equals(tradLang)){
				updateApplicationContext(model);
			} else {
				//If language is not tradional lang, then no need to set
				return;
			}
		}
		
		Boolean isTradionalLang = langCache.get(user.getUserId());
		if(null != isTradionalLang){
			if(isTradionalLang){
				updateApplicationContext(model);
			}
			return;
		}
		
		if(null != region){
			if(!(CountryEnum.CN.getName()).equals(region)){
				updateApplicationContext(model);
				//Cache the user
				langCache.put(user.getUserId(), Boolean.TRUE);
			} else {
				langCache.put(user.getUserId(), Boolean.FALSE);
			}
		}
	}
	
	/**
	 * Load the user region from cache first, if fail then retrieve from API.
	 * If API still fails, then return CN by default.
	 * @param userName
	 */
	private String getRegionFromCacheOrAPI(ModelAndView model, long uid , String userName){
		if(!StringUtil.isEmpty(userName)){
			String region = service.getUserCountryByName(userName, !EnviromentUtil.isProduction());
			if(!StringUtil.isEmpty(region)){
				//Cache the region
				regionCache.put(uid, region);
				model.addObject(ViewContext.Region.getAttr(), region);
				return region;
			}
		}
		//TODO need to put into enum.
		return "CN";
	}
	
	/**
	 * Update tasks to set or update the context.
	 * @param model
	 */
	private void updateApplicationContext(ModelAndView model){
		translatePromotionDescription(model);
		setLanguage(model);
	}
	
	/**
	 * Check if has promotion object in model, then do the translation.
	 * @param model
	 */
	private void translatePromotionDescription(ModelAndView model){
		try{
			Object promoObj = model.getModelMap().get(ViewContext.Promotion.getAttr());
			if(null != promoObj){
				Promotion promo = (Promotion) promoObj;
				String desc = promo.getDesc();
				if(!StringUtil.isEmpty(desc)){
					promo.setDesc(ZHConverter.convert(desc, ZHConverter.TRADITIONAL));
				}
			}
		} catch(Throwable e){
			//If NPE, then nothing happens. Should not fail the whole flow if translate fails.
		}
	}
	
	/**
	 * Set traditional language based on login user.
	 * @param model
	 */
	private void setLanguage(ModelAndView model){
		if(null != model && null != model.getViewName() && -1 == model.getViewName().indexOf(tradLang)){
			model.setViewName(tradLang + model.getViewName());
		}
	}

}
