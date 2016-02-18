package com.ebay.raptor.promotion.list.req;

import com.ebay.raptor.promotion.pojo.business.PromotionSubType;

public class ListingWebParam {
	
//	public static Long UID = 689917510l;
	
//	public static String SFID = "701O0000000QyWPIA0";

	private String promoId;

	private Long uid;
	
	private String promoSubType;

	public String getPromoId() {
		return promoId;
	}

	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getPromoSubType() {
		return promoSubType;
	}

	public void setPromoSubType(String promoSubType) {
		this.promoSubType = promoSubType;
	}

}
