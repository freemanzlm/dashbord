package com.ebay.raptor.promotion.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.service.PromotionService;
import com.ebay.raptor.promotion.service.ResourceProvider;

@Controller
@RequestMapping(ResourceProvider.PromotionRes.base)
public class PromotionDataController {

	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	PromotionService service;

	@GET
	@RequestMapping(ResourceProvider.PromotionRes.listPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> handleRequest() {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		resp.setData(service.getPromotions());
		return resp;
	}


}
