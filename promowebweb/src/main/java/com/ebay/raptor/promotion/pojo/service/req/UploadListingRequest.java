package com.ebay.raptor.promotion.pojo.service.req;

import java.util.List;

public class UploadListingRequest<T> {

	private String promoId;

	private Long uid;

	private List<T> listings;

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

	public List<T> getListings() {
		return listings;
	}

	public void setListings(List<T> listings) {
		this.listings = listings;
	}

}
