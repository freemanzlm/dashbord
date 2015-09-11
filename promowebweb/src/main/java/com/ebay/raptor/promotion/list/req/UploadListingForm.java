package com.ebay.raptor.promotion.list.req;

public class UploadListingForm {

	private String promoId;

	private Long uid;

	private String listings;

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

	public String getListings() {
		return listings;
	}

	public void setListings(String listings) {
		this.listings = listings;
	}

}
