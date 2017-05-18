package com.ebay.raptor.promotion.brand.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ebay.app.raptor.cbtcommon.CommonLogger;
import com.ebay.cbt.raptor.promotion.po.Promotion;
import com.ebay.cbt.raptor.promotion.route.ResourceProvider;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.BrandPerformance;
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
	 */
	public int countPassedBrandAmount(Long userId) {
		return 2;
	}

	public String getBrandIntroduction(Locale locale) {
		return "项目简介";
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

	public List<Promotion> getIngBrandPopuPromotions(Long userId) {
		List<Promotion> result = new ArrayList<Promotion>();
		List<Promotion> promos = null;
		try {
			promos = promotionService.getIngPromotion(userId);
		} catch (PromoException e) {
			e.printStackTrace();
		}
		if(!CollectionUtils.isEmpty(promos)){
			for (Promotion promo : promos) {
				
			}
		}
		return null;
	}
}
