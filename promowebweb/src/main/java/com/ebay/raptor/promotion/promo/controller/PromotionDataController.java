package com.ebay.raptor.promotion.promo.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.businesstype.PMPromotionType;
import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.web.resp.DataWebResponse;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.ContextViewRes;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.ResourceProvider;

@Controller
@RequestMapping(ResourceProvider.PromotionRes.base)
public class PromotionDataController{

	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	PromotionService service;
	
	@Autowired
	PromotionViewService view;
	
	@GET
	@RequestMapping("/{promoId}")
	@ResponseBody
	public ModelAndView promotion(@PathVariable("promoId") String promoId) {
		ModelAndView model = new ModelAndView();
		//TODO Get the uid from cookie
		Long uid = -1L;
		try {
			Promotion promo = service.getPromotionById(promoId, uid);
			if(null != promo){
				ContextViewRes res = handleViewBasedOnPromotion(promo);
				model.setViewName(res.getView().getPath());
				model.addAllObjects(res.getContext());
			}
		} catch (PromoException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	private ContextViewRes handleViewBasedOnPromotion(Promotion promo) throws PromoException{
		ContextViewRes result = new ContextViewRes();
		switch(PMPromotionType.valueOf(promo.getType())){
			case HIGH_VELOCITY:
				result = view.highVelocityView(promo);
				break;
			case DEALS_DASHBOARD_UPLOAD:
				result = view.dealsPresetView(promo);
				break;
			case DEALS_AM_UPLOAD:
				result = view.dealsUpload(promo);
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
	@RequestMapping(ResourceProvider.PromotionRes._getIngPromotion)
	@ResponseBody
	public ListDataWebResponse<Promotion> getIngPromotion(@RequestParam("uid") Long uid) {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		try {
			resp.setData(service.getPromotions(uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getSubsidyPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getSubsidyPromotions(@RequestParam("uid") Long uid) {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		try {
			resp.setData(service.getPromotions(uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getEndPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getEndPromotions(@RequestParam("uid") Long uid) {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		try {
			resp.setData(service.getPromotions(uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getPromotions(@RequestParam("uid") Long uid) {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		try {
			resp.setData(service.getPromotions(uid));
		} catch (PromoException e) {
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
			resp.setData(service.getPromotionById(promoId, uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}


}
