package com.ebay.raptor.promotion.list.controller;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.Listing;
import com.ebay.raptor.promotion.list.req.ListingWebParam;
import com.ebay.raptor.promotion.list.req.UploadListingForm;
import com.ebay.raptor.promotion.list.service.HotSellListingService;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.HotSellListing;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.PojoConvertor;


@Controller
@RequestMapping(ResourceProvider.ListingRes.hotsellBase)
public class HotSellListingController extends AbstractListingController{
	
	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	HotSellListingService service;
	
	@Autowired
	PromotionService promoService;
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.confirmHotSellListings)
	public ModelAndView confirmHotSellListings(HttpServletRequest req, @ModelAttribute UploadListingForm listings){
		ModelAndView model = new ModelAndView();
		if(null != listings){
			Listing[] listingAry = PojoConvertor.convertToObject(listings.getListings(), Listing[].class, false);
			try {
				UserData userData = CookieUtil.getUserDataFromCookie(req);
				if(service.confirmHotSellListings(listingAry, listings.getPromoId(), userData.getUserId())){
					model.setViewName(ViewResource.HV_APPLIED.getPath());
					Promotion promo = promoService.getPromotionById(listings.getPromoId(), userData.getUserId());
					model.addObject(ViewContext.Promotion.getAttr(), promo);
				} else {
					model.setViewName(ViewResource.ERROR.getPath());
				}
			} catch (PromoException | MissingArgumentException e) {
				model.setViewName(ViewResource.ERROR.getPath());
			}
		}
		return model;
	}

	@GET
	@RequestMapping(ResourceProvider.ListingRes._getApplicableListings)
	@ResponseBody
	public ListDataWebResponse<HotSellListing> getApplicableListings(HttpServletRequest req, @ModelAttribute ListingWebParam param) {
		ListDataWebResponse<HotSellListing> resp = new ListDataWebResponse<HotSellListing>();
		try {
			UserData userData = CookieUtil.getUserDataFromCookie(req);
			resp.setData(service.getApplicableListings(param.getPromoId(), userData.getUserId()));
		} catch (PromoException | MissingArgumentException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getAppliedListings)
	@ResponseBody
	public ListDataWebResponse<HotSellListing> getAppliedListings(HttpServletRequest req, @ModelAttribute ListingWebParam param) {
		ListDataWebResponse<HotSellListing> resp = new ListDataWebResponse<HotSellListing>();
		try {
			UserData userData = CookieUtil.getUserDataFromCookie(req);
			resp.setData(service.getAppliedListings(param.getPromoId(), userData.getUserId()));
		} catch (PromoException | MissingArgumentException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
}
