package com.ebay.raptor.promotion.list.service;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.Listing;
import com.ebay.raptor.promotion.pojo.business.APACDealsListing;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.FRESDealsListing;
import com.ebay.raptor.promotion.pojo.business.GBHDealsListing;
import com.ebay.raptor.promotion.pojo.business.PromotionSubType;
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

@Component
public class DealsListingService extends BaseService {

	private String url(String url){
		return secureUrl(ResourceProvider.ListingRes.dealsBase) + url;
	}
	
	private String siteUrl(String url){
		return secureUrl(ResourceProvider.ListingRes.siteDeals) + url;
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
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getPromotionListings(String promoId,
			Long uid, PromotionSubType promoSubType) throws PromoException{
		String uri = "";
		GenericType<?> type = null;

		if (promoSubType == null) {
			uri = url(params(ResourceProvider.ListingRes.getPromotionListings,
					new Object[] { "{promoId}", promoId, "{uid}", uid }));
			type = new GenericType<ListDataServiceResponse<DealsListing>>(){};
		} else {
			uri = siteUrl(params(
					ResourceProvider.ListingRes.getTempPromotionListings,
					new Object[] { "{promoId}", promoId, "{uid}", uid,
							"{type}", promoSubType }));
			switch (promoSubType) {
				case GBH:
					type = new GenericType<ListDataServiceResponse<GBHDealsListing>>(){};
					break;
				case FRES:
					type = new GenericType<ListDataServiceResponse<FRESDealsListing>>(){};
					break;
				case APAC:
					type = new GenericType<ListDataServiceResponse<APACDealsListing>>(){};
					break;
				default :
					throw new PromoException("Unrecognized promotion sub type.");
			}
		}

		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			ListDataServiceResponse<T> listing = (ListDataServiceResponse<T>)resp.getEntity(type);
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
	public <T> List<T> getUploadedListings(String promoId, Long uid, PromotionSubType promoSubType) throws PromoException{
		String uri = "";
		GenericType<?> type = null;

		if (promoSubType == null) {
			uri = url(params(ResourceProvider.ListingRes.getUploadedListings,
					new Object[] { "{promoId}", promoId, "{uid}", uid }));
			type = new GenericType<ListDataServiceResponse<DealsListing>>(){};
		} else {
			uri = siteUrl(params(
					ResourceProvider.ListingRes.getTempUploadedListings,
					new Object[] { "{promoId}", promoId, "{uid}", uid,
							"{type}", promoSubType }));
			switch (promoSubType) {
				case GBH:
					type = new GenericType<ListDataServiceResponse<GBHDealsListing>>(){};
					break;
				case FRES:
					type = new GenericType<ListDataServiceResponse<FRESDealsListing>>(){};
					break;
				case APAC:
					type = new GenericType<ListDataServiceResponse<APACDealsListing>>(){};
					break;
				default :
					throw new PromoException("Unrecognized promotion sub type.");
			}
		}

		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			ListDataServiceResponse<T> listing = (ListDataServiceResponse<T>)resp.getEntity(type);
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
	public <T> List<T> getSubmitedListings(String promoId, Long uid, PromotionSubType promoSubType) throws PromoException{
		String uri = "";
		GenericType<?> type = null;

		if (promoSubType == null) {
			uri = url(params(ResourceProvider.ListingRes.getSubmittedListings,
					new Object[] { "{promoId}", promoId, "{uid}", uid }));
			type = new GenericType<ListDataServiceResponse<DealsListing>>(){};
		} else {
			uri = siteUrl(params(
					ResourceProvider.ListingRes.getTempSubmittedListings,
					new Object[] { "{promoId}", promoId, "{uid}", uid,
							"{type}", promoSubType }));
			
			switch (promoSubType) {
				case GBH:
					type = new GenericType<ListDataServiceResponse<GBHDealsListing>>(){};
					break;
				case FRES:
					type = new GenericType<ListDataServiceResponse<FRESDealsListing>>(){};
					break;
				case APAC:
					type = new GenericType<ListDataServiceResponse<APACDealsListing>>(){};
					break;
				default :
					throw new PromoException("Unrecognized promotion sub type.");
			}
		}

		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			ListDataServiceResponse<T> listing = (ListDataServiceResponse<T>) resp.getEntity(type);
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
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getAppliedListings(String promoId, Long uid, PromotionSubType promoSubType) throws PromoException{
		String uri = "";
		GenericType<?> type = null;

		if (promoSubType == null) {
			uri = url(params(ResourceProvider.ListingRes.getAppliedListings,
					new Object[] { "{promoId}", promoId, "{uid}", uid }));
			type = new GenericType<ListDataServiceResponse<DealsListing>>(){};
		} else {
			uri = siteUrl(params(
					ResourceProvider.ListingRes.getTempAppliedListings,
					new Object[] { "{promoId}", promoId, "{uid}", uid,
							"{type}", promoSubType }));
			
			switch (promoSubType) {
				case GBH:
					type = new GenericType<ListDataServiceResponse<GBHDealsListing>>(){};
					break;
				case FRES:
					type = new GenericType<ListDataServiceResponse<FRESDealsListing>>(){};
					break;
				case APAC:
					type = new GenericType<ListDataServiceResponse<APACDealsListing>>(){};
					break;
				default :
					throw new PromoException("Unrecognized promotion sub type.");
			}
		}

		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			ListDataServiceResponse<T> listing = (ListDataServiceResponse<T>)resp.getEntity(type);
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
	public <T> List<T> getApprovedListings(String promoId, Long uid, PromotionSubType promoSubType) throws PromoException{
		String uri = "";
		GenericType<?> type = null;

		if (promoSubType == null) {
			uri = url(params(ResourceProvider.ListingRes.getApprovedListings,
					new Object[] { "{promoId}", promoId, "{uid}", uid }));
			type = new GenericType<ListDataServiceResponse<DealsListing>>(){};
		} else {
			uri = siteUrl(params(
					ResourceProvider.ListingRes.getTempApprovedListings,
					new Object[] { "{promoId}", promoId, "{uid}", uid,
							"{type}", promoSubType }));
			
			switch (promoSubType) {
				case GBH:
					type = new GenericType<ListDataServiceResponse<GBHDealsListing>>(){};
					break;
				case FRES:
					type = new GenericType<ListDataServiceResponse<FRESDealsListing>>(){};
					break;
				case APAC:
					type = new GenericType<ListDataServiceResponse<APACDealsListing>>(){};
					break;
				default :
					throw new PromoException("Unrecognized promotion sub type.");
			}
		}

		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			ListDataServiceResponse<T> listing = (ListDataServiceResponse<T>)resp.getEntity(type);
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
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getSkuListingsByPromotionId(String promoId, Long uid, PromotionSubType promoSubType) throws PromoException{
		String uri = "";
		GenericType<?> type = null;

		if (promoSubType == null) {
			uri = url(params(ResourceProvider.ListingRes.getSKUListingsByPromotionId,
					new Object[] { "{promoId}", promoId, "{uid}", uid }));
			type = new GenericType<ListDataServiceResponse<DealsListing>>(){};
		} else {
			uri = siteUrl(params(
					ResourceProvider.ListingRes.getSKUListingsByPromotionIdAndType,
					new Object[] { "{promoId}", promoId, "{uid}", uid,
							"{type}", promoSubType }));
			
			switch (promoSubType) {
				case GBH:
					type = new GenericType<ListDataServiceResponse<GBHDealsListing>>(){};
					break;
				case FRES:
					type = new GenericType<ListDataServiceResponse<FRESDealsListing>>(){};
					break;
				case APAC:
					type = new GenericType<ListDataServiceResponse<APACDealsListing>>(){};
					break;
				default :
					throw new PromoException("Unrecognized promotion sub type.");
			}
		}

		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			
			ListDataServiceResponse<T> data = (ListDataServiceResponse<T>)resp.getEntity(type);
			if(null != data && AckValue.SUCCESS == data.getAckValue()){
				return data.getData();
			}
		} else {
			throw new PromoException("Failed to retrieve the SKU list with provided promo ID: " + promoId);
		}
		return null;
	}
	

	public List<GBHDealsListing> getGBHListingsByPromotionId(String promoId, Long uid, PromotionSubType subType) throws PromoException{
		String uri = siteUrl(params(ResourceProvider.ListingRes.getListingsByPromotionIdAndUserIdAndType, new Object[]{"{promoId}", promoId, "{uid}", uid, "{type}", subType}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<GBHDealsListing>> type = new GenericType<ListDataServiceResponse<GBHDealsListing>>(){};
			ListDataServiceResponse<GBHDealsListing> data = resp.getEntity(type);
			if(null != data && AckValue.SUCCESS == data.getAckValue()){
				return data.getData();
			}
		} else {
			throw new PromoException("Failed to retrieve the GBH list with provided promo ID: " + promoId);
		}
		return null;
	}
	
	public List<FRESDealsListing> getFRESListingsByPromotionId(String promoId, Long uid, PromotionSubType subType) throws PromoException{
		String uri = siteUrl(params(ResourceProvider.ListingRes.getListingsByPromotionIdAndUserIdAndType, new Object[]{"{promoId}", promoId, "{uid}", uid, "{type}", subType}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<FRESDealsListing>> type = new GenericType<ListDataServiceResponse<FRESDealsListing>>(){};
			ListDataServiceResponse<FRESDealsListing> data = resp.getEntity(type);
			if(null != data && AckValue.SUCCESS == data.getAckValue()){
				return data.getData();
			}
		} else {
			throw new PromoException("Failed to retrieve the FRES list with provided promo ID: " + promoId);
		}
		return null;
	}
	
	public List<APACDealsListing> getAPACListingsByPromotionId(String promoId, Long uid, PromotionSubType subType) throws PromoException{
		String uri = siteUrl(params(ResourceProvider.ListingRes.getListingsByPromotionIdAndUserIdAndType, new Object[]{"{promoId}", promoId, "{uid}", uid, "{type}", subType}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<APACDealsListing>> type = new GenericType<ListDataServiceResponse<APACDealsListing>>(){};
			ListDataServiceResponse<APACDealsListing> data = resp.getEntity(type);
			if(null != data && AckValue.SUCCESS == data.getAckValue()){
				return data.getData();
			}
		} else {
			throw new PromoException("Failed to retrieve the APAC list with provided promo ID: " + promoId);
		}
		return null;
	}
	
	public boolean uploadDealsListings(List<DealsListing> uploadListings, String promoId, Long uid) throws PromoException {
		String uri = url(ResourceProvider.ListingRes.uploadDealsListings);
		UploadListingRequest<DealsListing> req = new UploadListingRequest<DealsListing>();
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
					return false;
				}
			} else {
				return false;
			}
		} else {
			throw new PromoException(ErrorType.UnableUploadDealsListing, Status.fromStatusCode(resp.getStatus()));
		}
	}
	
	public boolean uploadGBHDealsListings(List<GBHDealsListing> uploadListings, String promoId, Long uid) throws PromoException {
		String uri = siteUrl(ResourceProvider.ListingRes.uploadGBHDealsListings);
		UploadListingRequest<GBHDealsListing> req = new UploadListingRequest<GBHDealsListing>();
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
					return false;
				}
			} else {
				return false;
			}
		} else {
			throw new PromoException(ErrorType.UnableUploadDealsListing, Status.fromStatusCode(resp.getStatus()));
		}
	}
	
	public boolean uploadFRESDealsListings(List<FRESDealsListing> uploadListings, String promoId, Long uid) throws PromoException {
		String uri = siteUrl(ResourceProvider.ListingRes.uploadFRESDealsListings);
		UploadListingRequest<FRESDealsListing> req = new UploadListingRequest<FRESDealsListing>();
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
					return false;
				}
			} else {
				return false;
			}
		} else {
			throw new PromoException(ErrorType.UnableUploadDealsListing, Status.fromStatusCode(resp.getStatus()));
		}
	}
	
	public boolean uploadAPACDealsListings(List<APACDealsListing> uploadListings, String promoId, Long uid) throws PromoException {
		String uri = siteUrl(ResourceProvider.ListingRes.uploadAPACDealsListings);
		UploadListingRequest<APACDealsListing> req = new UploadListingRequest<APACDealsListing>();
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
					return false;
				}
			} else {
				return false;
			}
		} else {
			throw new PromoException(ErrorType.UnableUploadDealsListing, Status.fromStatusCode(resp.getStatus()));
		}
	}

	public boolean submitDealsListings(String promoId, Long uid) throws PromoException {
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
