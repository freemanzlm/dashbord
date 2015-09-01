package com.ebay.raptor.promotion.list.service;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.Listing;
import com.ebay.raptor.promotion.pojo.business.HotSellListing;
import com.ebay.raptor.promotion.pojo.service.req.UploadListingRequest;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.service.ResourceProvider;

import edu.emory.mathcs.backport.java.util.Arrays;

@Component
public class HotSellListingService extends BaseService {

	private String url(String url){
		return secureUrl(ResourceProvider.ListingRes.hotsellBase) + url;
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
	
	public List<HotSellListing> getApprovedListings(String promoId, Long uid) throws PromoException{
		String uri = url(params(ResourceProvider.ListingRes.getApprovedListings, new Object[]{"{promoId}", promoId, "{uid}", uid}));
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
	public boolean uploadPresetListings(Listing[] listingAry, String promoId, String uid) throws PromoException {
		String uri = url(ResourceProvider.ListingRes.uploadPresetListings);
		List<Listing> listingList = Arrays.asList(listingAry);
		UploadListingRequest req = new UploadListingRequest();
		req.setListings(listingList);;
		req.setPromoId(promoId);
		req.setUid(Long.parseLong(uid));
		GingerClientResponse resp = httpPost(uri, req);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			System.out.println(resp.getEntity(String.class));
//			GenericType<ListDataServiceResponse<HotSellListing>> type = new GenericType<ListDataServiceResponse<HotSellListing>>(){};
//			ListDataServiceResponse<HotSellListing> listing = resp.getEntity(type);
//			if(null != listing && AckValue.SUCCESS == listing.getAckValue()){
//				return listing.getData();
//			} else {
//				if(null != listing){
//					throw new PromoException(listing.getErrorMessage().getError().toString());
//				}
//			}
		} else {
			throw new PromoException("Internal Error Happens.");
		}
		return Boolean.FALSE;
	}
	
}
