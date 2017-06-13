package com.ebay.raptor.promotion.promo.service;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.binding.utils.CollectionUtils;
import com.ebay.cbt.raptor.promotion.po.Promotion;
import com.ebay.cbt.raptor.promotion.po.StatisticVO;
import com.ebay.cbt.raptor.promotion.po.SubsidyLegalTerm;
import com.ebay.cbt.raptor.promotion.route.ResourceProvider;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.GeneralDataResponse;
import com.ebay.raptor.promotion.pojo.service.resp.HasListingNominatedResponse;
import com.ebay.raptor.promotion.pojo.service.resp.ListDataServiceResponse;
import com.ebay.raptor.promotion.pojo.service.resp.PromoAcceptResponse;
import com.ebay.raptor.promotion.pojo.service.resp.PromotionResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.subsidy.service.SubsidyService;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Component
public class PromotionService extends BaseService {
	
	private CommonLogger logger = CommonLogger.getInstance(PromotionService.class);
	
	@Autowired
	private SubsidyService subsidyService;
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
		//By default the value is disabled, will re-enable this function for business.
		int i = 0;
		if(0 == i){
			return Boolean.FALSE;
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
	
	public List<Promotion> getUnconfirmedPromotions(Long uid, Boolean isAdmin) throws PromoException{
		return getPromotionsByUserBaseWithAdminFlag(ResourceProvider.PromotionRes.getUnconfirmedPromotions, uid, isAdmin);
	}
	
	public List<Promotion> getUpdatedPromotions(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getUpdatedPromotions, uid);
	}
	
	public List<Promotion> getIngPromotion(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getIngPromotions, uid);
	}
	
	public List<Promotion> getSubsidyPromotions(Long uid) throws PromoException{
		List<Promotion> promoList =  getPromotionsByUserBase(ResourceProvider.PromotionRes.getSubsidyPromotions, uid);
		if(null!=promoList){
			for (Promotion promo : promoList) {
				SubsidyLegalTerm term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), promo.getRegion());
				if(null!=term){
					if(term.getOvFlag()==0){
						promo.setOnlineVettingFlag(false);
					}else if (term.getOvFlag()==1) {
						promo.setOnlineVettingFlag(true);
					}else{
						promo.setOnlineVettingFlag(false);
					}
				}
			}
		}
		return promoList;
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
			throw new PromoException("Filed to get promotions.");
		}
		return null;
	}
	
	/**
	 * Base method to get promotion list.
	 * @param target
	 * @param uid isAdmin
	 * @return
	 * @throws PromoException
	 */
	private List<Promotion> getPromotionsByUserBaseWithAdminFlag(String target, Long uid, Boolean isAdmin) throws PromoException{
		String uri = url(params(target, new Object[]{"{uid}", uid, "{isadmin}", isAdmin}));
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
			throw new PromoException("Failed to get promotions.");
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
			throw new PromoException("Filed to get promotion.");
		}
		return null;
	}
	
	public boolean hasListingNominated(String promoId, Long uid) {
		try{
			String uri = url(params(ResourceProvider.PromotionRes.hasListingNominated, new Object[]{"{promoId}", promoId, "{uid}", uid}));
			GingerClientResponse resp = httpGet(uri);
			if(Status.OK.getStatusCode() == resp.getStatus()){
				HasListingNominatedResponse nominated = resp.getEntity(HasListingNominatedResponse.class);
				if(null != nominated && AckValue.SUCCESS == nominated.getAckValue()){
					return nominated.getHasListingNominated();
				}
			}
		} catch(Throwable e){
			logger.error(String.format("Failed to get whether nominated informatin"));
		}
		return Boolean.FALSE;
	}
	
	
	public List<Promotion> getIngBrandPromotion(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getIngBrandPromotions, uid);
	}
	
	public List<Promotion> awardingBrandPromotions(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getAwardingBrandPromotions, uid);
	}
	
	public List<Promotion> endedBrandPromotions(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getEndedBrandPromotions, uid);
	}

	public List<Promotion> getIngDealsPromotions(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getIngDealsPromotions, uid);
	}
	
	public List<Promotion> awardingDealsPromotions(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getAwardingDealsPromotions, uid);
	}
	
	public List<Promotion> endedDealsPromotions(Long uid) throws PromoException{
		return getPromotionsByUserBase(ResourceProvider.PromotionRes.getEndedDealsPromotions, uid);
	}
	
	public String promotionStatistics(Long uid){
		String uri = url(params(ResourceProvider.PromotionRes.promotionStatistics, new Object[]{"{uid}", uid}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<GeneralDataResponse<String>> type = new GenericType<GeneralDataResponse<String>>() {
			};
			GeneralDataResponse<String> info = resp.getEntity(type);
			return info.getData();
		} else {
			logger.error(String.format("Failed to get statistic  informatin"));
		}
		return null;
	}
}
