package com.ebay.raptor.promotion.interceptor;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContext;

import com.ebay.app.raptor.cbtcommon.pojo.db.Audit;
import com.ebay.app.raptor.cbtcommon.pojo.db.AuditType;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestException;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.config.AppConfig;
import com.ebay.raptor.promotion.config.AppCookies;
import com.ebay.raptor.promotion.nsd.pojo.TrackingType;
import com.ebay.raptor.promotion.nsd.service.NSDDataService;
import com.ebay.raptor.promotion.pojo.PGCSeller;
import com.ebay.raptor.promotion.pojo.PgcEligiblity;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.security.DES;
import com.ebay.raptor.promotion.service.BRDataService;
import com.ebay.raptor.promotion.service.BaseDataService;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.service.PGCService;
import com.ebay.raptor.promotion.service.TrackService;
import com.ebay.raptor.promotion.util.CookieUtil;

/**
 * 
 * @author lyan2
 */
public class LanguageInterceptor extends HandlerInterceptorAdapter {

	private final static Logger logger = Logger
			.getInstance(LanguageInterceptor.class);
	private static final String ISSUE_CODE_463  = "0";

	@Autowired
	BRDataService brdataService;
	@Autowired LoginService loginService;
	@Autowired BaseDataService baseService;
	@Autowired PGCService pgcService;
	@Autowired NSDDataService nsdService;
	@Autowired TrackService trackService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		 
		Map<String, String> cookieMap = CookieUtil.convertCookieToMap(request.getCookies());
		String adminUserName = cookieMap.get(AppCookies.EBAY_CBT_ADMIN_USER_COOKIE_NAME);
		if (adminUserName != null && !adminUserName.isEmpty()) {
			response.setHeader("X-Frame-Options", "Allow-From http://www.ebay.cn");
		}
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse resp, Object handler, ModelAndView model)
			throws Exception {
		if (null == model) {
			// skip when the service returns no ModelAndView
			return;
		}

		UserData user = loginService.getUserDataFromCookie(request);

		// get lang from cookie "eBayCBTLang" and parameter "lang"
		RequestContext context = new RequestContext(request);
		Locale locale = context.getLocale();
		String lang = locale.getLanguage() + "_" + locale.getCountry();
		
		// zh_HK and zh_TW are the same, so we just use zh_HK.
		if (lang.equalsIgnoreCase("zh_TW")) {
			lang = "zh_HK";
		}
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
		
		if (resp.getStatus() < HttpServletResponse.SC_BAD_REQUEST) {
			addPageParameters(request, model, user);
		}
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
		
		boolean isAdmin = userData.getAdmin();
		
		boolean accessBizReport = isAdmin;
		boolean isInDDSWhitelist = isAdmin;
		boolean isSubscribeDialogClosed = isAdmin;
		boolean isCanSubscribeConv = isAdmin;
		boolean isCanSubscribeDDS = isAdmin;
		boolean isInConvWhitelist = isAdmin;
		boolean accessAccountOverview=isAdmin;
		try {
//			accessBizReport = userData.getAdmin() || baseService.isUserAbleToAccessBizReport(userData.getUserId());

			if(!isAdmin) {
				Map<String, Boolean> subscriptionMsg  = brdataService.getSubscriptionMsg(userData.getUserId());
				if (subscriptionMsg != null)
				{
					isInDDSWhitelist = subscriptionMsg.get("isInDDSWhitelist") == null ? false: subscriptionMsg.get("isInDDSWhitelist");
					isSubscribeDialogClosed = subscriptionMsg.get("isSubscribeDialogClosed") == null ? false : subscriptionMsg.get("isSubscribeDialogClosed");
					isCanSubscribeConv = subscriptionMsg.get("isCanSubscribeConv") == null ? false : subscriptionMsg.get("isCanSubscribeConv");
					isCanSubscribeDDS = subscriptionMsg.get("isCanSubscribeDDS") == null ? false : subscriptionMsg.get("isCanSubscribeDDS");
					isInConvWhitelist = subscriptionMsg.get("isInConvWhitelist") == null ? false : subscriptionMsg.get("isInConvWhitelist");
					accessBizReport = isCanSubscribeConv || isCanSubscribeDDS || isInConvWhitelist;
				}
			}
			
		} catch (HttpRequestException e1) {
			logger.log(LogLevel.WARN, "Failed to check if user can access biz report.");
		}
		accessAccountOverview=nsdService.isUserAbleAccessNewBiz(userData.getUserId());
		long useractGrade = nsdService.getNSDActGradeByEbayOracleId(userData.getUserId()+"");
		boolean accessDiagnose=useractGrade>1?true:false;
		if(accessDiagnose==true){try {
			trackService.insertAudit(new Audit(TrackingType.NewDashBoardPromotion.getType(),userData.getUserId()+"",userData.getUserName(),""));//tracking nsd phase2 access new promotion
		} catch (com.ebay.app.raptor.cbtcommon.httpRequest.HttpRequestException e) {
			logger.log(LogLevel.ERROR,"Unable to insert login audit data into DB.");
		}}
		model.addObject("accessDiagnose",accessDiagnose );
		model.addObject("accessBiz", accessBizReport);
		model.addObject("accessAccountOverview",accessAccountOverview);
		model.addObject("isInDDSWhitelist", isInDDSWhitelist);
		model.addObject("isSubscribeDialogClosed", isSubscribeDialogClosed);
		model.addObject("isCanSubscribeConv", isCanSubscribeConv);
		model.addObject("isCanSubscribeDDS", isCanSubscribeDDS);
		model.addObject("isInConvWhitelist", isInConvWhitelist);
		
		// pgc quota
		renderPgcQuota(model, userData);
		
		if (accessBizReport) {
			model.addObject(ViewContext.BizUrl.getAttr(),
					AppConfig.BIZ_REPORT_URL + "?lang=" + language);
		}
		//access new phase1 version to new biz 
		if(accessAccountOverview){
			model.addObject(ViewContext.BizUrl.getAttr(),
					AppConfig.BIZ_REPORT_URL+"1" + "?lang=" + language);
		}

	}
	
	private void renderPgcQuota(ModelAndView mav, UserData userData) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("LoggedCBTAccount", userData.getUserName());
			obj.put("LoggedCBTAccountID", userData.getUserId());
			obj.put("time", System.currentTimeMillis());
			String secretParams = DES.getInstance().encrypt2(obj.toString());
			mav.addObject("secretParams", secretParams);
		} catch (Exception e) {
			logger.log(LogLevel.ERROR, "secretParams failed."+e.getMessage());
		}
		
		boolean pgcReady = false;
		try {
			pgcReady = baseService.getPgcFlag(userData.getUserId());
			mav.addObject("pgcReadyFlag", pgcReady);
		} catch (HttpRequestException e) {
			mav.addObject("pgcReadyFlag", false);
			logger.log(LogLevel.ERROR, "pgcReadyFlag failed."+e.getMessage());
			return;
		}
		
		if(pgcReady) {
			PgcEligiblity pgcEligible = null;
			try {
				pgcEligible = pgcService.getPgcEliblity(userData.getUserName());
			} catch (NumberFormatException | HttpRequestException e) {
				pgcEligible = new PgcEligiblity();
				logger.log(LogLevel.ERROR, "getPgcEliblity failed."+e.getMessage());
			}
			mav.addObject("pgcEligiblity", pgcEligible.getPgcEligibility());
			mav.addObject("hasIssue463", hasIssue463(pgcEligible));
			
			PGCSeller seller = null;
			try {
				seller = pgcService.getPGCAccount(userData.getUserName());
			} catch (NumberFormatException | HttpRequestException e) {
				seller = new PGCSeller();
				logger.log(LogLevel.ERROR, "getPGCAccount failed."+e.getMessage());
			}
			mav.addObject("pgcSeller", seller);
		}
	}
	
	private boolean hasIssue463(PgcEligiblity pgcEligible) {
		if(pgcEligible!=null && pgcEligible.getPgcStatusCode() !=null && pgcEligible.getPgcStatusCode().size()>0) {
			for(String issueCode:pgcEligible.getPgcStatusCode()) {
				if(issueCode.equalsIgnoreCase(ISSUE_CODE_463)) {
					return true;
				}
			}
		}
		return false;
	}

}
