package com.ebay.raptor.promotion.promo.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.businesstype.PMPromotionType;
import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.web.resp.DataWebResponse;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.ContextViewRes;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.CookieUtil;

@Controller
@RequestMapping(ResourceProvider.PromotionRes.base)
public class PromotionDataController{
	private static CommonLogger logger =
            CommonLogger.getInstance(PromotionDataController.class);

	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	PromotionService service;
	
	@Autowired
	PromotionViewService view;
	
	@Autowired DealsListingService dealsListingService;
	
	@Autowired ResourceBundleMessageSource messageSource;
	
	@GET
	@RequestMapping("/{promoId}")
	public ModelAndView promotion(HttpServletRequest request,
			@PathVariable("promoId") String promoId) throws MissingArgumentException {
		ModelAndView model = new ModelAndView();
//		UserData userData = CookieUtil.getUserDataFromCookie(request);

		try {
			Promotion promo = service.getPromotionById(promoId);

			if(null != promo){
				ContextViewRes res = handleViewBasedOnPromotion(promo);
				model.setViewName(res.getView().getPath());
				model.addAllObjects(res.getContext());
				model.addObject(ViewContext.Promotion.getAttr(), promo);
			}
		} catch (PromoException e) {
			logger.error("Unable to get promotion for " + promoId, e);
			model.setViewName(ViewResource.ERROR.getPath());
		}
		return model;
	}
	
	private ContextViewRes handleViewBasedOnPromotion(Promotion promo) throws PromoException{
		ContextViewRes result = new ContextViewRes();
		switch(PMPromotionType.valueOfPMType(promo.getType())){
			case HIGH_VELOCITY:
				result = view.highVelocityView(promo);
				break;
			case DEALS_DASHBOARD_UPLOAD:
				result = view.dealsUpload(promo);
				break;
			case DEALS_AM_UPLOAD:
				result = view.dealsPresetView(promo);
				break;
			case STANDARD:
				result = view.standard(promo);
				break;
			case PM_UNKNOWN_TYPE:
				result.setView(ViewResource.ERROR);
				break;
		}
		return result;
	}
	

	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getIngPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getIngPromotion(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = CookieUtil.getUserDataFromCookie(request);

		try {
			resp.setData(service.getIngPromotion(userData.getUserId()));
		} catch (PromoException e) {
			logger.error("Unable to get in-progress promotion of user " + userData.getUserId(), e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getSubsidyPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getSubsidyPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = CookieUtil.getUserDataFromCookie(request);
		try {
			resp.setData(service.getSubsidyPromotions(userData.getUserId()));
		} catch (PromoException e) {
			logger.error("Unable to get subsidy promotion of user " + userData.getUserId(), e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getEndPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getEndPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = CookieUtil.getUserDataFromCookie(request);
		try {
			resp.setData(service.getEndPromotions(userData.getUserId()));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = CookieUtil.getUserDataFromCookie(request);
		try {
			resp.setData(service.getPromotions(userData.getUserId()));
		} catch (PromoException e) {
			logger.error("Unable to get promotions of user " + userData.getUserId(), e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getPromotionById)
	@ResponseBody
	public DataWebResponse<Promotion> getPromotionById(@RequestParam("promoId")String promoId, @RequestParam("uid") Long uid) {
		DataWebResponse<Promotion> resp = new DataWebResponse<Promotion>();
		try {
			resp.setData(service.getPromotionById(promoId));
		} catch (PromoException e) {
			logger.error("Unable to get promotion of user " + uid + " and promotionID " + promoId, e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
}
