package com.ebay.raptor.promotion.interceptor;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContext;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.util.CommonConstant;
import com.ebay.app.raptor.promocommon.util.trans.ZHConverter;
import com.ebay.raptor.geo.utils.CountryEnum;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.service.BaseDataService;
import com.ebay.raptor.promotion.service.CSApiService;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.StringUtil;

public class LanguageInterceptor extends HandlerInterceptorAdapter {

	private final static CommonLogger logger = CommonLogger
			.getInstance(LanguageInterceptor.class);

	// TRUE: User is from HK/TW/MO, use traditional Chinese
	// FALSE: User is from CN, use Simplified Chinese
	private Map<Long, Boolean> langCache = new ConcurrentHashMap<Long, Boolean>();

	private Map<Long, String> regionCache = new ConcurrentHashMap<Long, String>();

	@Autowired
	private CSApiService service;
	@Autowired
	BaseDataService baseService;

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse resp, Object handler, ModelAndView model)
			throws Exception {
		if (null == model) {
			// skip when the service returns no ModelAndView
			return;
		}

		UserData user = CookieUtil.getUserDataFromCookie(request);

		// get lang from cookie "eBayCBTLang" and parameter "lang"
		RequestContext context = new RequestContext(request);
		Locale locale = context.getLocale();
		String lang = locale.getLanguage() + "_" + locale.getCountry();
		user.setLang(lang);

		if (model.hasView()) {
			String viewName = model.getViewName();
			
			// default locale is zh_CN
			if (viewName != null && !"zh_CN".equals(lang)
					&& viewName.indexOf(lang) == -1) {
				viewName = lang + "/" + viewName;
				model.setViewName(viewName);
			}
			logger.warn(viewName);
		}

		addPageParameters(request, model, user);

		resp.addHeader("X-Frame-Option", "Allow-From http://www.ebay.cn");

		// TODO Add CN by default.
		// model.addObject(ViewContext.Region.getAttr(), "CN");

		// Currently QATE env cannot call the CS API to fetch user region, so
		// skip all region related function.
		// if(AppBuildConfig.getInstance().isQATE()){
		// return;
		// }

		// regionBasedLanguageSetting(model, user);
		// model.addObject(ViewContext.Region.getAttr(),
		// getRegionFromCacheOrAPI(model, user.getUserId(),
		// user.getUserName()));
	}

	/**
	 * If user does not specify lang, then set user lang based on region.
	 */
	@Deprecated
	// Now region is from promotion & uid, so no need to call DAL to retrieve.
	private void regionBasedLanguageSetting(ModelAndView model, UserData user) {
		// Load language from cache first.
		Boolean isTradionalLang = langCache.get(user.getUserId());
		if (null != isTradionalLang) {
			if (isTradionalLang) {
				translatePromotionDescription(model);
			}
			return;
		}

		// Call the API CS api to get user
		String region = null;
		try {
			region = getRegionFromCacheOrAPI(model, user.getUserId(),
					user.getUserName());
		} catch (Throwable e) {
			logger.error("Failed to retrieved the region for seller  "
					+ user.getUserId() + ", error: " + e.getMessage());
		}

		if (null != region) {
			if (!(CountryEnum.CN.getName()).equals(region)) {
				translatePromotionDescription(model);
				// Cache the user
				langCache.put(user.getUserId(), Boolean.TRUE);
			} else {
				langCache.put(user.getUserId(), Boolean.FALSE);
			}
		}
	}

	/**
	 * Load the user region from cache first, if fail then retrieve from API. If
	 * API still fails, then return CN by default.
	 * 
	 * @param userName
	 */
	@Deprecated
	// No need to call API get region, directly fetch from DB.
	private String getRegionFromCacheOrAPI(ModelAndView model, long uid,
			String userName) {
		String region = regionCache.get(uid);
		// Load from cache
		if (!StringUtil.isEmpty(region)) {
			logger.error("Retrieved the region from cache for seller  " + uid
					+ ", region: " + region);
			return region;
		}

		if (!StringUtil.isEmpty(userName)) {
			region = service.getUserCountryByName(userName);
			if (!StringUtil.isEmpty(region)) {
				logger.error("Retrieved the region for seller  " + uid
						+ ", region: " + region);
				// Cache the region
				regionCache.put(uid, region);
				return region;
			}
		}
		return "";
	}

	private void addPageParameters(HttpServletRequest req, ModelAndView model,
			UserData userData) {
		String userName = userData.getUserName();
		String language = userData.getLang();

		// add username & biz report link
		model.addObject(ViewContext.UserName.getAttr(), userName);
		model.addObject(ViewContext.IsAdmin.getAttr(), userData.getAdmin());

		try {
			boolean accessBizReport = false;

			if (userData.getAdmin()) {
				accessBizReport = true;
			} else {
				// check if user is able to visit business report?
				accessBizReport = baseService
						.isUserAbleToAccessBizReport(userData.getUserId());
			}

			model.addObject("accessBiz", accessBizReport);
			if (accessBizReport) {
				model.addObject(ViewContext.BizUrl.getAttr(),
						CommonConstant.BIZ_REPORT_URL + "?lang" + language);
			}
		} catch (Exception e) {
			// ignore
		}

		// add the seller dashboard url
		model.addObject(ViewContext.SDUrl.getAttr(),
				CommonConstant.SELLER_DASHBOARD_URL);

		// for zh_HK, change the view name and translate the particular data
		// into traditional Chinese
		if (CommonConstant.ZHHK_LANGUAGE.equalsIgnoreCase(language)) {
			translatePromotionDescription(model);
		}
	}

	/**
	 * Check if has promotion object in model, then do the translation.
	 * 
	 * @param model
	 */
	private void translatePromotionDescription(ModelAndView model) {
		try {
			Object promoObj = model.getModelMap().get(
					ViewContext.Promotion.getAttr());
			if (null != promoObj) {
				Promotion promo = (Promotion) promoObj;
				String desc = promo.getDesc();
				if (!StringUtil.isEmpty(desc)) {
					promo.setDesc(ZHConverter.convert(desc,
							ZHConverter.TRADITIONAL));
				}
			}
		} catch (Throwable e) {
			// If NPE, then nothing happens. Should not fail the whole flow if
			// translate fails.
		}
	}

}
