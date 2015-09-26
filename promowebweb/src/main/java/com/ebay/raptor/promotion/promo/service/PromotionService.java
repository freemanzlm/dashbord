package com.ebay.raptor.promotion.promo.service;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.pojo.service.resp.PromoAcceptResponse;
import com.ebay.raptor.promotion.pojo.service.resp.PromotionResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.service.ResourceProvider;

@Component
public class PromotionService extends BaseService {
	
	private CommonLogger logger = CommonLogger.getInstance(PromotionService.class);

	private String url(String url){
		return secureUrl(ResourceProvider.PromotionRes.base) + url;
	}
	
	/**
	 * Check is accept agreement.
	 * @param promoId
	 * @param uid
	 * @return
	 */
	public boolean isAcceptAgreement(String promoId, long uid){
		//Accept agreement.
		//As requirement states, no need to accept the agreement after submit. 
		int i = 0;
		if(0 == i){
			return Boolean.TRUE;
		}
		try{
			String uri = url(params(ResourceProvider.PromotionRes.isAcceptAgreement, new Object[]{"{promoId}", promoId, "{uid}", uid}));
			GingerClientResponse resp = httpGet(uri);
			if(Status.OK.getStatusCode() == resp.getStatus()){
				PromoAcceptResponse accept = resp.getEntity(PromoAcceptResponse.class);
				if(null != accept && AckValue.SUCCESS == accept.getAckValue()){
					return accept.getIsAccept(); 
				}
			}
		} catch(Throwable e){
			logger.error(String.format("Failed to check is accept agreements for promotion %s uid %d, error %s.", promoId, uid, e.getMessage()));
		}
		return Boolean.FALSE;
	}
	
	public boolean acceptAgreement(String promoId, long uid){
		try{
			String uri = url(params(ResourceProvider.PromotionRes.acceptAgreement, new Object[]{"{promoId}", promoId, "{uid}", uid}));
			GingerClientResponse resp = httpGet(uri);
			if(Status.OK.getStatusCode() == resp.getStatus()){
				PromoAcceptResponse accept = resp.getEntity(PromoAcceptResponse.class);
				if(null != accept && AckValue.SUCCESS == accept.getAckValue()){
					return accept.getIsAccept();
				}
			}
		} catch(Throwable e){
			logger.error(String.format("Failed to accept agreements for promotion %s uid %d, error %s.", promoId, uid, e.getMessage()));
		}
		return Boolean.FALSE;
	}
	
	public List<Promotion> getUnconfirmedPromotions(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getUnconfirmedPromotions, uid);
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
	
	public Promotion getPromotionById(String promoId, Long uid, boolean isAdmin) throws PromoException{
		String uri = url(params(ResourceProvider.PromotionRes.getPromotionById, new Object[]{"{promoId}", promoId, "{uid}", uid, "{isAdmin}", isAdmin}));
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
