package com.ebay.raptor.promotion.subsidy.service;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.business.Subsidy;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.GeneralDataResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.service.ResourceProvider;

@Component
public class SubsidyService extends BaseService {
	private CommonLogger logger = CommonLogger.getInstance(SubsidyService.class);
	
	private String url(String url){
		return secureUrl(ResourceProvider.SubsidyRes.base) + url;
	}
	
	/**
	 * Get subsidy detail.
	 * @param promoId
	 * @param userId
	 * @return
	 * @throws PromoException
	 */
	public Subsidy getSubsidy(String promoId, Long userId) throws PromoException {
		String uri = url(params(ResourceProvider.SubsidyRes.getSubSidy, new Object[]{"promoId", promoId, "{uid}", userId}));
		GingerClientResponse resp = httpGet(uri);
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<GeneralDataResponse<Subsidy>> type = new GenericType<GeneralDataResponse<Subsidy>>(){};
			GeneralDataResponse<Subsidy> response = resp.getEntity(type);
			if(null != response && AckValue.SUCCESS == response.getAckValue()){
				return response.getData();
			} else {
				if(null != response && null != response.getErrorMessage() && null != response.getErrorMessage().getError()){
					throw new PromoException(response.getErrorMessage().getError().toString());
				}
			}
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return null;
	}
}
