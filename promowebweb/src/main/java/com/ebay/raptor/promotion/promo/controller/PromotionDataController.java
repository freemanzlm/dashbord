package com.ebay.raptor.promotion.promo.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.web.resp.DataWebResponse;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.service.ResourceProvider;

@Controller
@RequestMapping(ResourceProvider.PromotionRes.base)
public class PromotionDataController{
	private static CommonLogger logger =
            CommonLogger.getInstance(PromotionDataController.class);

	@Inject IRaptorContext raptorCtx;
	@Autowired LoginService loginService;
	@Autowired PromotionService service;
	
	@Autowired PromotionViewService view;
	
	@Autowired DealsListingService dealsListingService;
	
	@Autowired ResourceBundleMessageSource messageSource;
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._isAcceptAgreement)
	@ResponseBody
	public ListDataWebResponse<Promotion> isAcceptAgreement(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);

		try {
			resp.setData(service.getIngPromotion(userData.getUserId()));
		} catch (PromoException e) {
			logger.error("Unable to get in-progress promotion of user " + userData.getUserId(), e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._acceptAgreement)
	@ResponseBody
	public ListDataWebResponse<Promotion> acceptAgreement(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);

		try {
			resp.setData(service.getIngPromotion(userData.getUserId()));
		} catch (PromoException e) {
			logger.error("Unable to get in-progress promotion of user " + userData.getUserId(), e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getUnconfirmedPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getUnconfirmedPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);

		try {
			resp.setData(service.getUnconfirmedPromotions(userData.getUserId()));
		} catch (PromoException e) {
			logger.error("Unable to get unconfirmed promotions of user " + userData.getUserId(), e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getIngPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getIngPromotion(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);

		try {
			resp.setData(service.getIngPromotion(userData.getUserId()));
		} catch (PromoException e) {
			logger.error("Unable to get in-progress promotion of user " + userData.getUserId(), e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getSubsidyPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getSubsidyPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		try {
			resp.setData(service.getSubsidyPromotions(userData.getUserId()));
		} catch (PromoException e) {
			logger.error("Unable to get subsidy promotions of user " + userData.getUserId(), e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getEndPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getEndPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		try {
			resp.setData(service.getEndPromotions(userData.getUserId()));
		} catch (PromoException e) {
			logger.error("Unable to get end promotions of user " + userData.getUserId(), e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getPromotionById)
	@ResponseBody
	public DataWebResponse<Promotion> getPromotionById(HttpServletRequest request, 
			@RequestParam("promoId")String promoId, 
			@RequestParam("uid") Long uid) throws MissingArgumentException {
		DataWebResponse<Promotion> resp = new DataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		try {
			resp.setData(service.getPromotionById(promoId, uid, userData.getAdmin()));
		} catch (PromoException e) {
			logger.error("Unable to get promotion of user " + uid + " and promotionID " + promoId, e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
}
