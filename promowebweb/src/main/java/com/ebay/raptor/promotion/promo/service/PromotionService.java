package com.ebay.raptor.promotion.promo.service;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.pojo.service.resp.PromotionResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.service.ResourceProvider;

@Component
public class PromotionService extends BaseService {

	private String url(String url){
		return secureUrl(ResourceProvider.PromotionRes.base) + url;
	}
	
	public List<Promotion> getIngPromotion(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getIngPromotions, uid);
	}
	
	public List<Promotion> getSubsidyPromotions(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getSubsidyPromotions, uid);
	}
	
	public List<Promotion> getEndPromotions(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getEndPromotions, uid);
	}
	
	/**
	 * Base method to get promotion list.
	 * @param target
	 * @param uid
	 * @return
	 * @throws PromoException
	 */
	private List<Promotion> getPromotionsByUserBase(String target, Long uid) throws PromoException{
		String uri = url(params(target, new Object[]{"{uid}", uid}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<ListDataServiceResponse<Promotion>> type = new GenericType<ListDataServiceResponse<Promotion>>(){};
			ListDataServiceResponse<Promotion> promos = resp.getEntity(type);
			if(null != promos && AckValue.SUCCESS == promos.getAckValue()){
				return promos.getData();
			} else {
				if(null != promos && null != promos.getErrorMessage() && null != promos.getErrorMessage().getError()){
					throw new PromoException(promos.getErrorMessage().getError().toString());
				}
			}
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return null;
	}
	
//	public List<Promotion> getPromotions(Long uid) throws PromoException{
//		String uri = url(params(ResourceProvider.PromotionRes.getPromotions, new Object[]{"{uid}", uid}));
//		GingerClientResponse resp = httpGet(uri);
//		if(Status.OK.getStatusCode() == resp.getStatus()){
//			GenericType<ListDataServiceResponse<Promotion>> type = new GenericType<ListDataServiceResponse<Promotion>>(){};
//			ListDataServiceResponse<Promotion> promos = resp.getEntity(type);
//			if(null != promos && AckValue.SUCCESS == promos.getAckValue()){
//				return promos.getData();
//			} else {
//				if(null != promos && null != promos.getErrorMessage() && null != promos.getErrorMessage().getError()){
//					throw new PromoException(promos.getErrorMessage().getError().toString());
//				}
//			}
//		} else {
//			throw new PromoException("Internal Error happens.");
//		}
//		return null;
//	}
	
	public Promotion getPromotionById(String promoId) throws PromoException{
		String uri = url(params(ResourceProvider.PromotionRes.getPromotionById, new Object[]{"{promoId}", promoId}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			PromotionResponse promo = resp.getEntity(PromotionResponse.class);
			if(null != promo && AckValue.SUCCESS == promo.getAckValue()){
				return promo.getPromotion();
			} else {
				if(null != promo && null != promo.getErrorMessage() && null != promo.getErrorMessage().getError()){
					throw new PromoException(promo.getErrorMessage().getError().toString());
				}
			}
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return null;
	}
}
