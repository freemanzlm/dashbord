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
	public boolean confirmHotSellListings(Listing[] listingAry, String promoId, Long uid) throws PromoException {
		String uri = url(ResourceProvider.ListingRes.confirmHotSellListings);
		List<Listing> listingList = Arrays.asList(listingAry);
		UploadListingRequest<Listing> req = new UploadListingRequest<Listing>();
		req.setListings(listingList);;
		req.setPromoId(promoId);
		req.setUid(uid);
		GingerClientResponse resp = httpPost(uri, req);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			return Boolean.TRUE;
		} else {
			throw new PromoException("Internal Error Happens.");
		}
	}
	
}
