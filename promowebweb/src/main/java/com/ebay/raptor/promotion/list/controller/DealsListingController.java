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
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.businesstype.PMPromotionType;
import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.Listing;
import com.ebay.raptor.promotion.list.req.ListingWebParam;
import com.ebay.raptor.promotion.list.req.UploadListingForm;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.PojoConvertor;

@Controller
@RequestMapping(ResourceProvider.ListingRes.dealsBase)
public class DealsListingController {

	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	DealsListingService service;
	
	@Autowired
	PromotionService promoService;
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.confirmDealsListings)
	public ModelAndView confirmDealsListings(@ModelAttribute("listings") UploadListingForm listings) {
		ModelAndView model = new ModelAndView();
		if(null != listings){
			//TODO Add uid
			listings.setUid(ListingWebParam.UID);
			Listing[] listingAry = PojoConvertor.convertToObject(listings.getListings(), Listing[].class, false);
			try {
				boolean result = service.confirmDealsListings(listingAry, listings.getPromoId(), listings.getUid());
				if(result){
					Promotion promotion = promoService.getPromotionById(listings.getPromoId(), listings.getUid());
					model.addObject(ViewContext.Promotion.getAttr(), promotion);
					switch(PMPromotionType.valueOfPMType(promotion.getType())){
						case DEALS_DASHBOARD_UPLOAD:
							model.setViewName(ViewResource.DU_APPLIED.getPath());
							break;
						case DEALS_AM_UPLOAD:
							model.setViewName(ViewResource.DP_APPLIED.getPath());
							break;
						default:
							model.setViewName(ViewResource.ERROR.getPath());
							break;
					}
				}
			} catch (PromoException e) {
				model.setViewName(ViewResource.ERROR.getPath());
			}
		}
		return model;
	}

	@GET
	@RequestMapping(ResourceProvider.ListingRes._getApplicableListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getApplicableListings(@ModelAttribute ListingWebParam param) {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
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
	@RequestMapping(ResourceProvider.ListingRes.getSkuListingsByPromotionId)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getSkuList(@RequestParam("promoId")String promoId, @RequestParam("uid") Long uid) {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		resp.setData(service.getSkuListingByPromotionId(promoId, uid));
		return resp;
	}

}
