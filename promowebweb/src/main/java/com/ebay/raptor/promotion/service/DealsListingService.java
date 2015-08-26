package com.ebay.raptor.promotion.service;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;

@Component
public class DealsListingService extends BaseService {

	private String url(String url){
		return secureUrl(ResourceProvider.ListingRes.base) + url;
	}
	
	public List<DealsListing> getListing(){
		GingerClientResponse resp = httpGet(url(ResourceProvider.ListingRes.getApplicableListings));
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<DealsListing>> type = new GenericType<ListDataServiceResponse<DealsListing>>(){};
			ListDataServiceResponse<DealsListing> listing = resp.getEntity(type);
			if(null != listing && AckValue.SUCCESS == listing.getAckValue()){
				return listing.getData();
			}
		} else {
			System.out.println(resp.getStatus());
			System.out.println(resp.getEntity(String.class));
		}
		return null;
	}
	
	public List<Sku> getSku(){
		GingerClientResponse resp = httpGet(url(ResourceProvider.ListingRes.getSKUsByPromotionId));
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
