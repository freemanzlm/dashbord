package com.ebay.raptor.promotion.list.service;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.service.ResourceProvider;

@Component
public class DealsListingService extends BaseService {

	private String url(String url){
		return secureUrl(ResourceProvider.ListingRes.dealsBase) + url;
	}
	
	public List<DealsListing> getApplicableListings(String promoId, Long uid) throws PromoException{
		String uri = url(params(ResourceProvider.ListingRes.getApplicableListings, new Object[]{"{promoId}", promoId, "{uid}", uid}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<DealsListing>> type = new GenericType<ListDataServiceResponse<DealsListing>>(){};
			ListDataServiceResponse<DealsListing> listing = resp.getEntity(type);
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
	
	public List<DealsListing> getAppliedListings(String promoId, Long uid) throws PromoException{
		String uri = url(params(ResourceProvider.ListingRes.getAppliedListings, new Object[]{"{promoId}", promoId, "{uid}", uid}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<DealsListing>> type = new GenericType<ListDataServiceResponse<DealsListing>>(){};
			ListDataServiceResponse<DealsListing> listing = resp.getEntity(type);
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
	
	public List<DealsListing> getApprovedListings(String promoId, Long uid) throws PromoException{
		String uri = url(params(ResourceProvider.ListingRes.getApprovedListings, new Object[]{"{promoId}", promoId, "{uid}", uid}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<DealsListing>> type = new GenericType<ListDataServiceResponse<DealsListing>>(){};
			ListDataServiceResponse<DealsListing> listing = resp.getEntity(type);
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
	
	//TODO Refine
	public List<Sku> getSKUsByPromotionId(String promoId, Long uid){
		String uri = url(params(ResourceProvider.ListingRes.getSKUsByPromotionId, new Object[]{"{promoId}", promoId, "{uid}", uid}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<Sku>> type = new GenericType<ListDataServiceResponse<Sku>>(){};
			ListDataServiceResponse<Sku> data = resp.getEntity(type);
			if(null != data && AckValue.SUCCESS == data.getAckValue()){
				return data.getData();
			}
		} else {
			System.out.println(resp.getStatus());
			System.out.println(resp.getEntity(String.class));
		}
		return null;
	}
	
	
}
