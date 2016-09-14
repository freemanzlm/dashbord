package com.ebay.raptor.promotion.list.service;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.SelectableListing;
import com.ebay.raptor.promotion.pojo.business.HotSellListing;
import com.ebay.raptor.promotion.pojo.service.req.UploadListingRequest;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.pojo.service.resp.UploadListingResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.service.ResourceProvider;

import edu.emory.mathcs.backport.java.util.Arrays;

@Component
public class HotSellListingService extends BaseService {

	private String url(String url){
		return secureUrl(ResourceProvider.ListingRes.hotsellBase) + url;
	}
	
	public List<HotSellListing> getPromotionListing(String promoId, Long uid) throws PromoException{
		String uri = url(params(ResourceProvider.ListingRes.getPromotionListings, new Object[]{"{promoId}", promoId, "{uid}", uid}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<HotSellListing>> type = new GenericType<ListDataServiceResponse<HotSellListing>>(){};
			ListDataServiceResponse<HotSellListing> listing = resp.getEntity(type);
			if(null != listing && AckValue.SUCCESS == listing.getAckValue()){
				return listing.getData();
			} else {
				if(null != listing){
					throw new PromoException(listing.getErrorMessage().getError().toString());
				}
			}
		} else {
			throw new PromoException("Internal Error Happens.");
		}
		return null;
	}
	
	public List<HotSellListing> getApplicableListings(String promoId, Long uid) throws PromoException{
		String uri = url(params(ResourceProvider.ListingRes.getApplicableListings, new Object[]{"{promoId}", promoId, "{uid}", uid}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<HotSellListing>> type = new GenericType<ListDataServiceResponse<HotSellListing>>(){};
			ListDataServiceResponse<HotSellListing> listing = resp.getEntity(type);
			if(null != listing && AckValue.SUCCESS == listing.getAckValue()){
				return listing.getData();
			} else {
				if(null != listing){
					throw new PromoException(listing.getErrorMessage().getError().toString());
				}
			}
		} else {
			throw new PromoException("Internal Error Happens.");
		}
		return null;
	}
	
	public List<HotSellListing> getAppliedListings(String promoId, Long uid) throws PromoException{
		String uri = url(params(ResourceProvider.ListingRes.getAppliedListings, new Object[]{"{promoId}", promoId, "{uid}", uid}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<HotSellListing>> type = new GenericType<ListDataServiceResponse<HotSellListing>>(){};
			ListDataServiceResponse<HotSellListing> listing = resp.getEntity(type);
			if(null != listing && AckValue.SUCCESS == listing.getAckValue()){
				return listing.getData();
			} else {
				if(null != listing){
					throw new PromoException(listing.getErrorMessage().getError().toString());
				}
			}
		} else {
			throw new PromoException("Internal Error Happens.");
		}
		return null;
	}
	

	@SuppressWarnings("unchecked")
	public boolean confirmHotSellListings(SelectableListing[] listingAry, String promoId, Long uid) throws PromoException {
		String uri = url(ResourceProvider.ListingRes.confirmHotSellListings);
		List<SelectableListing> listingList = Arrays.asList(listingAry);
		UploadListingRequest<SelectableListing> req = new UploadListingRequest<SelectableListing>();
		req.setListings(listingList);;
		req.setPromoId(promoId);
		req.setUid(uid);
		GingerClientResponse resp = httpPost(uri, req);
		try{
			GenericType<UploadListingResponse<HotSellListing>> type = new GenericType<UploadListingResponse<HotSellListing>>(){};
			if(null != resp){
				UploadListingResponse<HotSellListing> respEntity = resp.getEntity(type);
				if(null != respEntity && respEntity.getAckValue() == AckValue.SUCCESS){
					return Boolean.TRUE;
				}
			}
		} catch(Throwable e){
			throw new PromoException("Internal Error Happens.");
		}
		return Boolean.FALSE;
	}
	
}
