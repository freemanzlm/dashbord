package com.ebay.raptor.promotion.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.pojo.business.Listing;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.service.ListingService;
import com.ebay.raptor.promotion.service.ResourceProvider;

@Controller
@RequestMapping(ResourceProvider.ListingRes.base)
public class ListingController {

	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	ListingService service;

	@GET
	@RequestMapping(ResourceProvider.ListingRes.getApplicableListings)
	@ResponseBody
	public ListDataWebResponse<Listing> handleRequest() {
		ListDataWebResponse<Listing> resp = new ListDataWebResponse<Listing>();
		resp.setData(service.getListing());
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes.getSKUsByPromotionId)
	@ResponseBody
	public ListDataWebResponse<Sku> getSkuList() {
		ListDataWebResponse<Sku> resp = new ListDataWebResponse<Sku>();
		resp.setData(service.getSku());
		return resp;
	}


}
