package com.ebay.raptor.promotion.promo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.cbt.common.constant.pm.PMPromoTabType;
import com.ebay.cbt.raptor.promotion.po.Promotion;
import com.ebay.cbt.raptor.promotion.route.ResourceProvider;
import com.ebay.kernel.calwrapper.CalEventHelper;
import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.Router;
import com.ebay.raptor.promotion.brand.service.BrandService;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.UserData;
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
	@Autowired BrandService brandService;
	
	@Autowired PromotionViewService view;
	
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
	
	// **************************** need test *********************//
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.brandRegPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getBrandRegPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		List<Promotion> data = null;
		try {
			data = brandService.getBrandAuthPromotions(userData.getUserId());
		} catch (PromoException e) {
			e.printStackTrace();
			logger.log(e.getMessage());
		}
		if(!CollectionUtils.isEmpty(data)){
			resp.setData(data);
		}
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.endedDealsPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getEndedDealsPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		List<Promotion> data = null;
		try {
			data = service.endedDealsPromotions(userData.getUserId());
		} catch (PromoException e) {
			e.printStackTrace();
			logger.log(e.getMessage());
		}
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
		List<Promotion> data = null;
		try {
			data = service.getIngDealsPromotions(userData.getUserId());
		} catch (PromoException e) {
			e.printStackTrace();
			logger.log(e.getMessage());
		}
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
		List<Promotion> data = null;
		try {
			data = service.awardingDealsPromotions(userData.getUserId());
		} catch (PromoException e) {
			e.printStackTrace();
			logger.log(e.getMessage());
		}
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
		List<Promotion> data = null;
		List<Promotion> result = new ArrayList<Promotion>();
		try {
			data = service.getUnconfirmedPromotions(userData.getUserId(),userData.getAdmin());
		} catch (PromoException e) {
			e.printStackTrace();
			logger.log(e.getMessage());
		}
		if(!CollectionUtils.isEmpty(data)){
			for (Promotion promo : data) {
				if(promo.getType() != null && PMPromoTabType.DEALS.getTypeId()==promo.getType()){
					result.add(promo);
				}
			}
		}
		resp.setData(result);
		return resp;
	}
	
	@AuthNeed
	@GET
	@RequestMapping(Router.Promotion.endedBrandPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getEndedBrandPromotions(HttpServletRequest request) throws MissingArgumentException {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		UserData userData = loginService.getUserDataFromCookie(request);
		List<Promotion> data = null;
		try {
			data = service.endedBrandPromotions(userData.getUserId());
		} catch (PromoException e) {
			e.printStackTrace();
			logger.log(e.getMessage());
		}
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
		List<Promotion> data = null;
		try {
			data = service.getIngBrandPromotion(userData.getUserId());
		} catch (PromoException e) {
			e.printStackTrace();
			logger.log(e.getMessage());
		}
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
		List<Promotion> data = null;
		try {
			data = service.awardingBrandPromotions(userData.getUserId());
		} catch (PromoException e) {
			e.printStackTrace();
			logger.log(e.getMessage());
		}
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
		List<Promotion> data = null;
		List<Promotion> result = new ArrayList<Promotion>();
		try {
			data = service.getUnconfirmedPromotions(userData.getUserId(),userData.getAdmin());
		} catch (PromoException e) {
			e.printStackTrace();
			logger.log(e.getMessage());
		}
		if(!CollectionUtils.isEmpty(data)){
			for (Promotion promo : data) {
				if(promo.getType() != null && PMPromoTabType.BRAND_PROMO.getTypeId()==promo.getType()){
					result.add(promo);
				}
			}
		}
		resp.setData(result);
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
	
	@GET
	@RequestMapping(Router.Promotion.promoStatistics)
	@ResponseBody
	public DataWebResponse<Map<String, Object>> getPromotionStatistics(HttpServletRequest request) throws MissingArgumentException {
		DataWebResponse<Map<String, Object>> resp = new DataWebResponse<Map<String, Object>>();
		UserData userData = loginService.getUserDataFromCookie(request);
		List<Promotion> ingData = null;
		List<Promotion> awardData = null;
		Map<String, Object> result = new HashMap<String, Object>();
		int allCount = 0;
		int brandCount = 0;
		int vettingCount = 0;
		int dealsCount = 0;
		try {
//			data = service.getUpdatedPromotions(userData.getUserId());
			ingData = service.getIngPromotion(userData.getUserId());
			awardData = service.awardingBrandPromotions(userData.getUserId());
		} catch (PromoException e) {
			e.printStackTrace();
			logger.log(e.getMessage());
		}
		if(!CollectionUtils.isEmpty(ingData)){
			allCount = ingData.size();
			for (Promotion promo : ingData) {
				if(promo.getType() != null && PMPromoTabType.BRAND_PROMO.getTypeId()==promo.getType()){
					brandCount++;
				}else if(promo.getType() != null && PMPromoTabType.BRAND_VETTING.getTypeId()==promo.getType()){
					vettingCount++;
				}else if(promo.getType() != null && PMPromoTabType.DEALS.getTypeId()==promo.getType()){
					dealsCount++;
				}
			}
		}
		if(!CollectionUtils.isEmpty(awardData)){
			allCount += awardData.size();
			for (Promotion promo : awardData) {
				if(promo.getType() != null && PMPromoTabType.BRAND_PROMO.getTypeId()==promo.getType()){
					brandCount++;
				}else if(promo.getType() != null && PMPromoTabType.BRAND_VETTING.getTypeId()==promo.getType()){
					vettingCount++;
				}else if(promo.getType() != null && PMPromoTabType.DEALS.getTypeId()==promo.getType()){
					dealsCount++;
				}
			}
		}
		
		result.put("all", allCount);
		result.put("brand", brandCount);
		result.put("vetting", vettingCount);
		result.put("deals", dealsCount);
		
		resp.setStatus(true);
		resp.setData(result);
		return resp;
	}
	
	@ExceptionHandler(Exception.class)
	public BaseWebResponse handleException(Exception exception, HttpServletRequest request) {
		CalEventHelper.writeException("Exception", exception, true);
		BaseWebResponse resp = new BaseWebResponse();
		resp.setStatus(false);
		resp.setMessage(exception.getMessage());
		return resp;
	}
}
