package com.ebay.raptor.promotion.subsidy.service;

import java.util.ArrayList;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.cbt.raptor.po.Subsidy;
import com.ebay.cbt.raptor.po.WLTAccount;
import com.ebay.cbt.raptor.promotion.po.SubsidyCustomField;
import com.ebay.cbt.raptor.promotion.response.SubsidyLegalTermResponse;
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
	 * 
	 * @param paymentType
	 * @return
	 */
	public SubsidyLegalTermResponse getSubsidyLegalTerm(Integer paymentType, String country) {
		SubsidyLegalTermResponse term = new SubsidyLegalTermResponse();
		ArrayList<SubsidyCustomField> sellerFillingFields =  new ArrayList<SubsidyCustomField>();
		SubsidyCustomField field = new SubsidyCustomField();
		field.setKey("id");
		field.setLabel("中国公民身份证ID");
		field.setValue("4234324324234324");
		sellerFillingFields.add(field);
		
		term.setCountry(country);
		term.setOnlingVettingFlag(1);
		
		term.setSellerFillingFields(sellerFillingFields);
		
		return term;
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
		System.out.println("-------------------httpclientè°ç¨æ¶é´-------------------"+(stop1-start));
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
			System.out.println("-------------------æ°æ®è§£ææ¶é´-------------------"+(stop2-stop1));
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return null;
	}
	
}
