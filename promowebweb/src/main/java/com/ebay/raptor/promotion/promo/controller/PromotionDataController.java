package com.ebay.raptor.promotion.promo.controller;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.Router;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.web.resp.BaseWebResponse;
import com.ebay.raptor.promotion.pojo.web.resp.DataWebResponse;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.service.LoginService;

@Controller
@RequestMapping(Router.Promotion.base)
public class PromotionDataController{
	private static CommonLogger logger =
            CommonLogger.getInstance(PromotionDataController.class);

	@Inject IRaptorContext raptorCtx;
	@Autowired LoginService loginService;
	@Autowired PromotionService service;
	
	@Autowired PromotionViewService view;
	
	@Autowired ResourceBundleMessageSource messageSource;
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.getUnconfirmedPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getUnconfirmedPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);

		try {
			resp.setData(service.getUnconfirmedPromotions(userData.getUserId(), userData.getAdmin()));
		} catch (PromoException e) {
			logger.error("Unable to get unconfirmed promotions of user " + userData.getUserId(), e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.getIngPromotions)
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
	@RequestMapping(Router.Promotion.getSubsidyPromotions)
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
	@RequestMapping(Router.Promotion.getEndPromotions)
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
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.brandRegPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getBrandRegPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		
		// TODO get brand registration promotions
		ArrayList<Promotion> data = new ArrayList<Promotion>();
		
		Promotion p = new Promotion();
		p.setName("Brand registion promotion");
		
		data.add(p);
		
		resp.setData(data);
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.endedDealsPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getEndedDealsPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		
		// TODO get brand registration promotions
		ArrayList<Promotion> data = new ArrayList<Promotion>();
		
		Promotion p = new Promotion();
		p.setName("Brand registion promotion");
		
		data.add(p);
		
		resp.setData(data);
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.ongoingDealsPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getOngoingDealsPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		
		// TODO get brand registration promotions
		ArrayList<Promotion> data = new ArrayList<Promotion>();
		
		Promotion p = new Promotion();
		p.setName("Brand registion promotion");
		p.setCurrentStep("SELLER NOMINATION_NEED APPROVE");
		
		data.add(p);
		
		resp.setData(data);
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.awardingDealsPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getAwardingDealsPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		
		// TODO get brand registration promotions
		ArrayList<Promotion> data = new ArrayList<Promotion>();
		
		Promotion p = new Promotion();
		p.setName("Brand registion promotion");
		
		data.add(p);
		
		resp.setData(data);
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.unpublishedDealsPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getUnpublishedDealsPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		
		// TODO get brand registration promotions
		ArrayList<Promotion> data = new ArrayList<Promotion>();
		
		Promotion p = new Promotion();
		p.setName("Brand registion promotion");
		p.setCurrentStep("SELLER NOMINATION_NEED APPROVE");
		
		data.add(p);
		
		resp.setData(data);
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.endedBrandPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getEndedBrandPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		
		// TODO get brand registration promotions
		ArrayList<Promotion> data = new ArrayList<Promotion>();
		
		Promotion p = new Promotion();
		p.setName("Brand registion promotion");
		
		data.add(p);
		
		resp.setData(data);
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.ongoingBrandPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getOngoingBrandPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		
		// TODO get brand registration promotions
		ArrayList<Promotion> data = new ArrayList<Promotion>();
		
		Promotion p = new Promotion();
		p.setName("Brand registion promotion");
		p.setCurrentStep("SELLER NOMINATION_NEED APPROVE");
		
		data.add(p);
		
		resp.setData(data);
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.awardingBrandPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getAwardingBrandPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		
		// TODO get brand registration promotions
		ArrayList<Promotion> data = new ArrayList<Promotion>();
		
		Promotion p = new Promotion();
		p.setName("Brand registion promotion");
		
		data.add(p);
		
		resp.setData(data);
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.unpublishedBrandPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getUnpublishedBrandPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		
		// TODO get brand registration promotions
		ArrayList<Promotion> data = new ArrayList<Promotion>();
		
		Promotion p = new Promotion();
		p.setName("Brand registion promotion");
		p.setCurrentStep("SELLER NOMINATION_NEED APPROVE");
		
		data.add(p);
		
		resp.setData(data);
		return resp;
	}
	
	@GET
	@RequestMapping(Router.Promotion.getPromotionById)
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
	
	@ExceptionHandler(Exception.class)
	public BaseWebResponse handleException(Exception exception, HttpServletRequest request) {
		logger.error(exception.getMessage(), exception);
		BaseWebResponse resp = new BaseWebResponse();
		resp.setStatus(false);
		resp.setMessage(exception.getMessage());
		return resp;
	}
}
