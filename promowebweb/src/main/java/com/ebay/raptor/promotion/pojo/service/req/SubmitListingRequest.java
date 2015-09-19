package com.ebay.raptor.promotion.pojo.service.req;

public class SubmitListingRequest {

	private String promoId;

	private Long uid;

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
}
