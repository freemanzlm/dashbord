package com.ebay.raptor.promotion.subsidy.service;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.cbt.raptor.po.Subsidy;
import com.ebay.cbt.raptor.po.WLTAccount;
import com.ebay.cbt.raptor.route.ResourceProvider;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.GeneralDataResponse;
import com.ebay.raptor.promotion.service.BaseService;

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
		String uri = url(params(ResourceProvider.SubsidyRes.getSubSidy, new Object[]{"{promoId}", promoId, "{uid}", userId}));
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
	
	/**
	 * Get subsidy detail.
	 * @param promoId
	 * @param userId
	 * @return
	 * @throws PromoException
	 */
	public WLTAccount getTest(String promoId, Long userId) throws PromoException {
		String uri = url(params(ResourceProvider.SubsidyRes.getTest, new Object[]{"{promoId}", promoId, "{uid}", userId}));
		long start = System.currentTimeMillis();
		System.out.println("-------------------start-------------------"+start);
		GingerClientResponse resp = httpGet(uri);
		long stop1 = System.currentTimeMillis();
		System.out.println("-------------------httpclient调用时间-------------------"+(stop1-start));
		if(Status.OK.getStatusCode() == resp.getStatus()){
			GenericType<GeneralDataResponse<WLTAccount>> type = new GenericType<GeneralDataResponse<WLTAccount>>(){};
			GeneralDataResponse<WLTAccount> response = resp.getEntity(type);
			if(null != response && AckValue.SUCCESS == response.getAckValue()){
				return response.getData();
			} else {
				if(null != response && null != response.getErrorMessage() && null != response.getErrorMessage().getError()){
					throw new PromoException(response.getErrorMessage().getError().toString());
				}
			}
			long stop2 = System.currentTimeMillis();
			System.out.println("-------------------数据解析时间-------------------"+(stop2-stop1));
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return null;
	}
	
}
