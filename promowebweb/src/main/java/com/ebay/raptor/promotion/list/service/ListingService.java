package com.ebay.raptor.promotion.list.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.ebayopensource.ginger.client.GingerWebTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.kernel.util.URLDecoder;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.SelectableListing;
import com.ebay.raptor.promotion.pojo.business.Listing;
import com.ebay.raptor.promotion.pojo.business.ListingAttachment;
import com.ebay.raptor.promotion.pojo.service.req.SubmitListingRequest;
import com.ebay.raptor.promotion.pojo.service.req.UploadListingRequest;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.GeneralDataResponse;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.pojo.service.resp.UploadListingResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.service.IAFTokenService;
import com.ebay.raptor.promotion.service.PromoClient;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * This listing service is used to get listing data from RESTFul service.
 * 
 * @author lyan2
 */
@Component
public class ListingService extends BaseService {
	private CommonLogger logger = CommonLogger.getInstance(ListingService.class);
	@Autowired ResourceBundleMessageSource msgResource;
	private Locale locale;
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
	public Boolean confirmListings(SelectableListing[] listings, String promoId, Long uid) throws PromoException{
		String uri = url(ResourceProvider.ListingRes.confirmListings);
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
	 * Get seller applied sku listings in this promotion. These listings are uploaded by seller.
	 * @param promoId
	 * @param uid
	 * @param promoSubType
	 * @return
	 * @throws PromoException
	 */
	public <T> List<T> getListingsByPromotionId(String promoId, Long uid, boolean isUploaded) throws PromoException{
		String uri = "";
		GenericType<?> type = null;

		if(isUploaded)
			uri = url(params(ResourceProvider.ListingRes.getUploadedListings,
					new Object[] { "{promoId}", promoId, "{uid}", uid }));
		else
			uri = url(params(ResourceProvider.ListingRes.getPromotionListings,
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
		String uri = url(ResourceProvider.ListingRes.uploadListings);
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
		String uri = url(ResourceProvider.ListingRes.submitPromoListings);
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
	
	/**
	 * Upload listing attachment into database.
	 * @param listingId
	 * @param uploadFile
	 * @param fileType
	 * @return
	 * @throws PromoException
	 * @throws IOException 
	 */
	public String uploadListingAttachment(String skuId, String promoId, Long userId, String key, final MultipartFile uploadFile, String fileType) throws Exception {
		String url = url(ResourceProvider.ListingRes.uploadListingAttachment);
		FormDataMultiPart multiPart = new FormDataMultiPart();
		File file = multipartToFile(uploadFile);
	    FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file",
	            file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
	    multiPart.bodyPart(fileDataBodyPart);
	    multiPart.field("skuId", skuId);
	    multiPart.field("promoId", promoId);
	    multiPart.field("userId", Long.toString(userId));
	    multiPart.field("fileType", fileType);
	    multiPart.field("key", key);
	    multiPart.field("fileName", decodefilePathOrfileName(file.getName()));
		GingerClientResponse resp = uploadMultipart(url, multiPart);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<GeneralDataResponse<Boolean>> type = new GenericType<GeneralDataResponse<Boolean>>(){};
			GeneralDataResponse<Boolean> general = resp.getEntity(type);
			if(null != general){
				if (AckValue.SUCCESS == general.getAckValue()) {
					return params(ResourceProvider.ListingRes.listingAttachment,
							new Object[] { "{promoId}", promoId, "{userId}", userId, "{skuId}", skuId, "{key}", key});
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			//TODO change the error type.
			throw new Exception(getMessage("attachment.validation.message.notcorrecttype"));
		}
	}
	
	private String decodefilePathOrfileName(String value) {
		try {
			return URLDecoder.decode(
					new String(value.getBytes(),"UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}
	
	/**
	 * Download listing attachment into database.
	 * @param skuId
	 * @param userId
	 * @param promoId
	 * @return boolean
	 * @throws PromoException 
	 */
	public ListingAttachment downloadListingAttachment(String promoId, Long userId, String skuId, String key) throws PromoException {
		String url = url(params(ResourceProvider.ListingRes.listingAttachment,
				new Object[] { "{promoId}", promoId, "{userId}", userId, "{skuId}", skuId, "{key}", key}));
		GingerWebTarget target = PromoClient.getClient().target(url);
		Invocation.Builder build = target.request();
		Response resp =  build.headers(authHeaders(IAFTokenService.getIAFToken())).get();
		if(Status.OK.getStatusCode() == resp.getStatus()) {
			MultivaluedMap<String, Object> headers = resp.getMetadata();
			String attachmentName = (String) headers.get("attachmentName").get(0);
			if(attachmentName==null || "".equals(attachmentName)) {
				attachmentName = "download"; //default download file name
			}
			String attachmentType = (String) headers.get("attachmentType").get(0);
			InputStream inputStream = (InputStream) resp.getEntity();
			ListingAttachment attachment = new ListingAttachment();
			attachment.setAttachmentName(attachmentName);
			attachment.setAttachmentType(attachmentType);
			attachment.setContent(inputStream);
			return attachment;
		} else {
			throw new PromoException(ErrorType.UnableSubmitDealsListing, Status.fromStatusCode(resp.getStatus()));
		}
	}
	
	private File multipartToFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		File file = new File(multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);
		return file;
	} 
	
	public Locale getLocale() {
		return locale == null ? LocaleContextHolder.getLocale() : locale;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	private String getMessage(String key){
		return msgResource.getMessage(key, null, getLocale());
	}
}
