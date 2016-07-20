package com.ebay.raptor.promotion.list.service;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.Listing;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.service.req.SubmitListingRequest;
import com.ebay.raptor.promotion.pojo.service.req.UploadListingRequest;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.GeneralDataResponse;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.pojo.service.resp.UploadListingResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.service.ResourceProvider;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * This listing service is used to get listing data from RESTFul service.
 * 
 * @author lyan2
 */
@Component
public class ListingService extends BaseService {
	
	/**
	 * TODO
	 * @param url
	 * @return
	 */
	private String url(String url){
		// TODO, change to ResourceProvider.ListingRes.base
		return secureUrl(ResourceProvider.ListingRes.base) + url;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean confirmDealsListings(Listing[] listings, String promoId, Long uid) throws PromoException{
		String uri = url(ResourceProvider.ListingRes.confirmDealsListings);
		List<Listing> listingList = Arrays.asList(listings);
		UploadListingRequest<Listing> req = new UploadListingRequest<Listing>();
		req.setListings(listingList);
		req.setPromoId(promoId);
		req.setUid(uid);
		GingerClientResponse resp = httpPost(uri, req);
		try{
			GenericType<UploadListingResponse<Listing>> type = new GenericType<UploadListingResponse<Listing>>(){};
			if(null != resp){
				UploadListingResponse<Listing> respEntity = resp.getEntity(type);
				if(null != respEntity && respEntity.getAckValue() == AckValue.SUCCESS){
					return Boolean.TRUE;
				}
			}
		} catch(Throwable e){
			throw new PromoException("Internal Error Happens.");
		}
		return Boolean.FALSE;
	}

	/**
	 * Get seller sku list in this promotion. Returned list is for sku list table.
	 * 
	 * @param promoId Promotion SF ID
	 * @param uid User oracle ID
	 * @return
	 * @throws PromoException
	 */
	public List<Sku> getSkusByPromotionId(String promoId, Long uid) throws PromoException{
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
	
	/**
	 * Get seller applied sku listings in this promotion. These listings are uploaded by seller.
	 * @param promoId
	 * @param uid
	 * @param promoSubType
	 * @return
	 * @throws PromoException
	 */
	public <T> List<T> getSkuListingsByPromotionId(String promoId, Long uid) throws PromoException{
		String uri = "";
		GenericType<?> type = null;

		uri = url(params(ResourceProvider.ListingRes.getSKUListingsByPromotionId,
				new Object[] { "{promoId}", promoId, "{uid}", uid }));
//		type = new GenericType<ListDataServiceResponse<Map<String, Object>>>(){};
		type = new GenericType<ListDataServiceResponse<Listing>>(){};

		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			@SuppressWarnings("unchecked")
			ListDataServiceResponse<T> data = (ListDataServiceResponse<T>)resp.getEntity(type);
			if(null != data && AckValue.SUCCESS == data.getAckValue()){
				return data.getData();
			}
		} else {
			throw new PromoException("Failed to retrieve the SKU listing list with provided promo ID: " + promoId + ", user ID: " + uid);
		}
		return null;
	}
	
	/**
	 * Upload user uploaded listing into database.
	 * @param uploadListings
	 * @param promoId
	 * @param uid
	 * @return
	 * @throws PromoException
	 */
	public boolean uploadListings(List<Listing> uploadListings, String promoId, Long uid) throws PromoException {
		String uri = url(ResourceProvider.ListingRes.uploadDealsListings);
		UploadListingRequest<Listing> req = new UploadListingRequest<Listing>();
		req.setListings(uploadListings);
		req.setPromoId(promoId);
		req.setUid(uid);
		GingerClientResponse resp = httpPost(uri, req);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<GeneralDataResponse<Boolean>> type = new GenericType<GeneralDataResponse<Boolean>>(){};
			GeneralDataResponse<Boolean> general = resp.getEntity(type);
			if(null != general){
				if (AckValue.SUCCESS == general.getAckValue()) {
					return true;
				} else {
					int errorCode = general.getResponseStatus();
	
					if (errorCode == ErrorType.DateExpiredException.getCode()) {
						throw new PromoException(ErrorType.DateExpiredException, "ACTION1_END_DATE");
					}
				}
			}
		}
		
		throw new PromoException(ErrorType.UnableUploadDealsListing, Status.fromStatusCode(resp.getStatus()));
	}
	
	/**
	 * Submit all uploaded listing from Database to SalesForce.
	 * 
	 * @param promoId
	 * @param uid
	 * @return
	 * @throws PromoException
	 */
	public boolean submitListings(String promoId, Long uid) throws PromoException {
		String uri = url(ResourceProvider.ListingRes.submitDealsListings);
		SubmitListingRequest req = new SubmitListingRequest();
		req.setPromoId(promoId);
		req.setUid(uid);
		GingerClientResponse resp = httpPost(uri, req);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<GeneralDataResponse<Boolean>> type = new GenericType<GeneralDataResponse<Boolean>>(){};
			GeneralDataResponse<Boolean> general = resp.getEntity(type);
			if(null != general){
				if (AckValue.SUCCESS == general.getAckValue()) {
					return true;
				} else {
					int errorCode = general.getResponseStatus();
	
					if (errorCode == ErrorType.DateExpiredException.getCode()) {
						throw new PromoException(ErrorType.DateExpiredException, "ACTION1_END_DATE");
					}
					return false;
				}
			} else {
				
				return false;
			}
		} else {
			throw new PromoException(ErrorType.UnableSubmitDealsListing, Status.fromStatusCode(resp.getStatus()));
		}
	}
}
