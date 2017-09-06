package com.ebay.raptor.promotion.nsd.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.cbtcommon.pojo.db.AuditType;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.cbt.raptor.csApi.service.CSApiService;
import com.ebay.cbt.raptor.promotion.enumcode.PromotionStep;
import com.ebay.cbt.raptor.promotion.po.Promotion;
import com.ebay.cbt.raptor.promotion.po.Subsidy;
import com.ebay.cbt.raptor.promotion.po.SubsidyLegalTerm;
import com.ebay.kernel.calwrapper.CalEventHelper;
import com.ebay.kernel.context.AppBuildConfig;
import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.kernel.error.RaptorErrorData;
import com.ebay.raptor.kernel.util.RaptorConstants;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.brand.service.BrandService;
import com.ebay.raptor.promotion.config.AppCookies;
import com.ebay.raptor.promotion.enums.PromoError;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.RequestParameter;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.sd.service.SDDataService;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.service.TrackService;
import com.ebay.raptor.promotion.subsidy.service.SubsidyService;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.LocaleUtil;
import com.ebay.raptor.siteApi.util.SiteApiUtil;

@Controller
public class IndexController {

	@Autowired IRaptorContext raptorCtx;
	@Autowired CSApiService csApiService;
	@Autowired LoginService loginService;
	@Autowired PromotionService service;
	@Autowired PromotionViewService view;
	@Autowired SubsidyService subsidyService;
	@Autowired TrackService trackService;
	@Autowired SDDataService sdDataService;
	@Autowired BrandService brandService;
	@Autowired ResourceBundleMessageSource msgResource;


	@AuthNeed
	@RequestMapping(value = "/index1", method = RequestMethod.GET)
	public ModelAndView handleIndexRequest(HttpServletRequest request, HttpServletResponse response, @ModelAttribute RequestParameter param)
			throws MissingArgumentException {
		ModelAndView mav = new ModelAndView();
		// Set unconfirmed status
		UserData userDt = loginService.getUserDataFromCookie(request);
		mav.addObject(ViewContext.IsAdmin.getAttr(), userDt.getAdmin());

		mav.setViewName("index");

		// add track record to DB
		trackService.logUserActivityAsync(userDt, AuditType.VisitToPromo, "");
		return mav;
	}
	
	

}