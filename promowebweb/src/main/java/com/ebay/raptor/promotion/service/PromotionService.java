package com.ebay.raptor.promotion.service;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.business.UserPromotion;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.GeneralServiceResponse;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;

@Component
public class PromotionService extends BaseService {

	private String url(String url){
		return secureUrl(ResourceProvider.PromotionRes.base) + url;
	}
	
	public List<Promotion> getPromotions(){
		GingerClientResponse resp = httpGet(url(ResourceProvider.PromotionRes.listing));
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<Promotion>> type = new GenericType<ListDataServiceResponse<Promotion>>(){};
			ListDataServiceResponse<Promotion> promos = resp.getEntity(type);
			if(null != promos && AckValue.SUCCESS == promos.getAckValue()){
				return promos.getData();
			}
		} else {
			System.out.println(resp.getStatus());
			System.out.println(resp.getEntity(String.class));
		}
		return null;
	}

	/*
	 * Get User Promotion detail data.
	 */
	public UserPromotion getPromotionDetail(Long userId, String promoId){
		GingerClientResponse resp = httpGet(url(ResourceProvider.UserPromotionRes.userPromo)); // TODO - 
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<GeneralServiceResponse<UserPromotion>> type = new GenericType<GeneralServiceResponse<UserPromotion>>(){};
			GeneralServiceResponse<UserPromotion> promos = resp.getEntity(type);
			if(null != promos && AckValue.SUCCESS == promos.getAckValue()){
				return promos.getData();
			}
		} else {
			System.out.println(resp.getStatus());
			System.out.println(resp.getEntity(String.class));
		}
		return null;
	}
}
