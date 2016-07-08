package com.ebay.raptor.promotion.list.service;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.service.ResourceProvider;

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
		return secureUrl(ResourceProvider.ListingRes.dealsBase) + url;
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
		type = new GenericType<ListDataServiceResponse<Map<String, Object>>>(){};

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
}
