package com.ebay.raptor.promotion.list.service;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.Listing;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.service.req.UploadListingRequest;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.service.ResourceProvider;

import edu.emory.mathcs.backport.java.util.Arrays;

@Component
public class DealsListingService extends BaseService {

	private String url(String url){
		return secureUrl(ResourceProvider.ListingRes.dealsBase) + url;
	}
	

	@SuppressWarnings("unchecked")
	public Boolean confirmDealsListings(Listing[] listings, String promoId, Long uid) throws PromoException{
		String uri = url(ResourceProvider.ListingRes.confirmDealsListings);
		List<Listing> listingList = Arrays.asList(listings);
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
	
	public List<Sku> getSkuListingByPromotionId(String promoId, Long uid) throws PromoException{
		String uri = url(params(ResourceProvider.ListingRes.getSKUsByPromotionId, new Object[]{"{promoId}", promoId, "{uid}", uid}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<Sku>> type = new GenericType<ListDataServiceResponse<Sku>>(){};
			ListDataServiceResponse<Sku> data = resp.getEntity(type);
			if(null != data && AckValue.SUCCESS == data.getAckValue()){
				return data.getData();
			}
		} else {
			throw new PromoException("Failed to retrieve the SKU list with provided promo ID: " + promoId);
		}
		return null;
	}
	
	public boolean uploadDealsListings(List<DealsListing> uploadListings, String promoId, Long uid) throws PromoException {
		String uri = url(ResourceProvider.ListingRes.uploadDealsListings);
		UploadListingRequest<DealsListing> req = new UploadListingRequest<DealsListing>();
		req.setListings(uploadListings);;
		req.setPromoId(promoId);
		req.setUid(uid);
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
			return true;
		} else {
			throw new PromoException(ErrorType.UnableUploadDealsListing, Status.fromStatusCode(resp.getStatus()));
		}
	}
	
	public boolean confirmDealsListings(List<Listing> listings, String promoId, String uid) throws PromoException {
		String uri = url(ResourceProvider.ListingRes.confirmDealsListings);
		UploadListingRequest<Listing> req = new UploadListingRequest<Listing>();
		req.setListings(listings);;
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
