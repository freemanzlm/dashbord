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
import com.ebay.raptor.promotion.list.req.ListingWebParam;
import com.ebay.raptor.promotion.list.req.UploadListingForm;
import com.ebay.raptor.promotion.list.service.HotSellListingService;
import com.ebay.raptor.promotion.pojo.business.HotSellListing;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.PojoConvertor;


@Controller
@RequestMapping(ResourceProvider.ListingRes.hotsellBase)
public class HotSellListingController {

	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	HotSellListingService service;
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.confirmHotSellListings)
	@ResponseBody
	public ListDataWebResponse<HotSellListing> confirmHotSellListings(@ModelAttribute UploadListingForm listings){
		ListDataWebResponse<HotSellListing> resp = new ListDataWebResponse<HotSellListing>();
		if(null != listings){
			Listing[] listingAry = PojoConvertor.convertToObject(listings.getListings(), Listing[].class, false);
			try {
				service.confirmHotSellListings(listingAry, listings.getPromoId(), listings.getUid());
			} catch (PromoException e) {
				resp.setStatus(Boolean.FALSE);
			}
		}
		return resp;
	}

	@GET
	@RequestMapping(ResourceProvider.ListingRes._getApplicableListings)
	@ResponseBody
	public ListDataWebResponse<HotSellListing> getApplicableListings(@ModelAttribute ListingWebParam param) {
		ListDataWebResponse<HotSellListing> resp = new ListDataWebResponse<HotSellListing>();
		try {
			resp.setData(service.getApplicableListings(param.getPromoId(), param.getUid()));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getAppliedListings)
	@ResponseBody
	public ListDataWebResponse<HotSellListing> getAppliedListings(@RequestParam("promoId")String promoId, @RequestParam("uid") Long uid) {
		ListDataWebResponse<HotSellListing> resp = new ListDataWebResponse<HotSellListing>();
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
	public ListDataWebResponse<HotSellListing> getApprovedListings(@RequestParam("promoId")String promoId, @RequestParam("uid") Long uid) {
		ListDataWebResponse<HotSellListing> resp = new ListDataWebResponse<HotSellListing>();
		try {
			resp.setData(service.getApprovedListings(promoId, uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}

}
