package com.ebay.raptor.promotion.list.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.Listing;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.service.ResourceProvider;

@Controller
@RequestMapping(ResourceProvider.ListingRes.dealsBase)
public class DealsListingController {

	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	DealsListingService service;
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.uploadDealsListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> uploadDealsListings(@ModelAttribute("listings") Listing listings, 
			@RequestParam("promoId")String promoId, @RequestParam("uid") Long uid) {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
//		try {
//			resp.setData(service.getApplicableListings(promoId, uid));
//		} catch (PromoException e) {
//			resp.setStatus(Boolean.FALSE);
//		}
		return resp;
	}

	@GET
	@RequestMapping(ResourceProvider.ListingRes._getApplicableListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getApplicableListings(@RequestParam("promoId")String promoId, @RequestParam("uid") Long uid) {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		try {
			resp.setData(service.getApplicableListings(promoId, uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getAppliedListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getAppliedListings(@RequestParam("promoId")String promoId, @RequestParam("uid") Long uid) {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		try {
			resp.setData(service.getAppliedListings(promoId, uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getApprovedListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getApprovedListings(@RequestParam("promoId")String promoId, @RequestParam("uid") Long uid) {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		try {
			resp.setData(service.getApprovedListings(promoId, uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes.getSKUsByPromotionId)
	@ResponseBody
	public ListDataWebResponse<Sku> getSkuList(@RequestParam("promoId")String promoId, @RequestParam("uid") Long uid) {
		ListDataWebResponse<Sku> resp = new ListDataWebResponse<Sku>();
		resp.setData(service.getSKUsByPromotionId(promoId, uid));
		return resp;
	}


}
