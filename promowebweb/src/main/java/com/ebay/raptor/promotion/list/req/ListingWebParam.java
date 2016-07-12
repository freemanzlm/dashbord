package com.ebay.raptor.promotion.list.req;

/**
 * 
 * @author lyan2
 */
public class ListingWebParam {
	
	private String promoId;

	private Long uid;
	
	// TODO Remove this field in phase2
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
