package com.ebay.raptor.promotion.pojo.service.req;

import java.util.List;

import com.ebay.raptor.promotion.list.req.Listing;

public class UploadListingRequest {

	private String promoId;

	private Long uid;

	private List<Listing> listings;

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

	public List<Listing> getListings() {
		return listings;
	}

	public void setListings(List<Listing> listings) {
		this.listings = listings;
	}

}
