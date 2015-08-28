package com.ebay.raptor.promotion.controller.promo;

import javax.inject.Inject;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.web.resp.DataWebResponse;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.service.PromotionService;
import com.ebay.raptor.promotion.service.ResourceProvider;

@Controller
@RequestMapping(ResourceProvider.PromotionRes.base)
public class PromotionDataController{

	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	PromotionService service;

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
