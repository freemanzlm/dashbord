package com.ebay.raptor.promotion.brand.service;

import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.cbtcommon.CommonLogger;
import com.ebay.cbt.raptor.promotion.po.BrandPerformance;
import com.ebay.cbt.raptor.promotion.po.Promotion;
import com.ebay.cbt.raptor.promotion.route.ResourceProvider;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.service.resp.GeneralDataResponse;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.service.BaseService;

/**
 * 
 * @author lyan2
 */
@Component
public class BrandService extends BaseService {
	
	private CommonLogger Logger = CommonLogger.getInstance(BrandService.class);

	@Autowired
	private PromotionService promotionService;

	private String url(String url) {
		return secureUrl(ResourceProvider.PromotionRes.base) + url;
	}

	/**
	 * Calculate how many brands of a user has passed authentication.
	 * 
	 * @param userId
	 * @return
	 * @throws PromoException 
	 */
	public int countPassedBrandAmount(Long userId) throws PromoException {
		String uri = url(params(ResourceProvider.PromotionRes.countBrandPerformance, new Object[] { "{uid}", userId }));
		GingerClientResponse resp = httpGet(uri);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<Integer>> type = new GenericType<GeneralDataResponse<Integer>>() {
			};
			GeneralDataResponse<Integer> countData = resp.getEntity(type);
			return countData.getData();
		} else {
			throw new PromoException("BrandPerformanceCount not found!");
		}
	}

	public String getBrandIntroduction(Locale locale){
		String uri = url(params(ResourceProvider.PromotionRes.getBrandIntroduction, new Object[] { "{country}", locale.toString() }));
		GingerClientResponse resp = httpGet(uri);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<String>> type = new GenericType<GeneralDataResponse<String>>() {
			};
			GeneralDataResponse<String> info = resp.getEntity(type);
			return info.getData();
		} else {
			Logger.log("BrandVettingIntroduction not found!");
			return null;
		}
	}

	public List<BrandPerformance> getBrandPerformance(Long userId) throws PromoException {
		String uri = url(params(ResourceProvider.PromotionRes.getBrandPerformance, new Object[] { "{uid}", userId }));
		GingerClientResponse resp = httpGet(uri);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<List<BrandPerformance>>> type = new GenericType<GeneralDataResponse<List<BrandPerformance>>>() {
			};
			GeneralDataResponse<List<BrandPerformance>> brands = resp.getEntity(type);
			return brands.getData();
		} else {
			throw new PromoException("BrandPerformance not found!");
		}
	}

	public List<Promotion> getBrandAuthPromotions(Long userId) throws PromoException {
		String uri = url(params(ResourceProvider.PromotionRes.getBrandAuthPromotions, new Object[] { "{uid}", userId }));
		GingerClientResponse resp = httpGet(uri);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<List<Promotion>>> type = new GenericType<GeneralDataResponse<List<Promotion>>>() {
			};
			GeneralDataResponse<List<Promotion>> promos = resp.getEntity(type);
			return promos.getData();
		} else {
			throw new PromoException("BrandPerformance not found!");
		}
	}
	
}