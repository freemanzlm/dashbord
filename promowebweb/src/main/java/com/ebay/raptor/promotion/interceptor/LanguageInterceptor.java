package com.ebay.raptor.promotion.interceptor;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContext;

import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestException;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.config.AppConfig;
import com.ebay.raptor.promotion.config.AppCookies;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.service.BaseDataService;
import com.ebay.raptor.promotion.service.CSApiService;

/**
 * 
 * @author lyan2
 */
public class LanguageInterceptor extends HandlerInterceptorAdapter {

	private final static Logger logger = Logger
			.getInstance(LanguageInterceptor.class);

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

		UserData user = AppCookies.getUserDataFromCookie(request);

		// get lang from cookie "eBayCBTLang" and parameter "lang"
		RequestContext context = new RequestContext(request);
		Locale locale = context.getLocale();
		String lang = locale.getLanguage() + "_" + locale.getCountry();
		user.setLang(lang);

		// zh_HK and zh_TW are the same, so we just use zh_HK.
		if (lang.equalsIgnoreCase("zh_TW")) {
			lang = "zh_HK";
		}
		user.setLang(lang);

		if (model.hasView()) {
			String viewName = model.getViewName();
			
			// default locale is zh_CN
			if (viewName != null && !"zh_CN".equals(lang)
					&& viewName.indexOf(lang) == -1) {
				viewName = lang + "/" + viewName;
				model.setViewName(viewName);
			}
		}

		addPageParameters(request, model, user);

		resp.addHeader("X-Frame-Option", "Allow-From http://www.ebay.cn");
	}

	private void addPageParameters(HttpServletRequest req, ModelAndView model,
			UserData userData) {
		String userName = userData.getUserName();
		String language = userData.getLang();

		// add username & biz report link & dashboard link
		model.addObject(ViewContext.UserName.getAttr(), userName);
		model.addObject(ViewContext.UserId.getAttr(), userData.getUserId());
		model.addObject(ViewContext.IsAdmin.getAttr(), userData.getAdmin());
		model.addObject(ViewContext.SDUrl.getAttr(), AppConfig.SELLER_DASHBOARD_URL);
		
		boolean accessBizReport = false;
		try {
//			accessBizReport = userData.getAdmin() || baseService.isUserAbleToAccessBizReport(userData.getUserId());

			boolean isInDDSWhitelist = false;
			boolean isSubscribeDialogClosed = false;
			boolean isCanSubscribeConv = false;
			boolean isCanSubscribeDDS = false;
			boolean isInConvWhitelist = false;
			Map<String, Boolean> subscriptionMsg  = baseService.getSubscriptionMsg(userData.getUserId());

			if (subscriptionMsg != null)
			{
				isInDDSWhitelist = subscriptionMsg.get("isInDDSWhitelist") == null ? false: subscriptionMsg.get("isInDDSWhitelist");
				model.addObject("isInDDSWhitelist", isInDDSWhitelist);

				isSubscribeDialogClosed = subscriptionMsg.get("isSubscribeDialogClosed") == null ? false : subscriptionMsg.get("isSubscribeDialogClosed");
				model.addObject("isSubscribeDialogClosed", isSubscribeDialogClosed);

				isCanSubscribeConv = subscriptionMsg.get("isCanSubscribeConv") == null ? false : subscriptionMsg.get("isCanSubscribeConv");
				model.addObject("isCanSubscribeConv", isCanSubscribeConv);

				isCanSubscribeDDS = subscriptionMsg.get("isCanSubscribeDDS") == null ? false : subscriptionMsg.get("isCanSubscribeDDS");
				model.addObject("isCanSubscribeDDS", isCanSubscribeDDS);

				isInConvWhitelist = subscriptionMsg.get("isInConvWhitelist") == null ? false : subscriptionMsg.get("isInConvWhitelist");
				model.addObject("isInConvWhitelist", isInConvWhitelist);
				
				accessBizReport = userData.getAdmin() || isCanSubscribeConv || isCanSubscribeDDS;
			}
			
		} catch (HttpRequestException e1) {
			logger.log(LogLevel.WARN, "Failed to check if user can access biz report.");
		}
		
		model.addObject("accessBiz", accessBizReport);
		
		if (accessBizReport) {
			model.addObject(ViewContext.BizUrl.getAttr(),
					AppConfig.BIZ_REPORT_URL + "?lang" + language);
		}

	}

}
