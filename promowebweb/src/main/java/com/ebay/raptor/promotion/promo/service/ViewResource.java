package com.ebay.raptor.promotion.promo.service;


public enum ViewResource {

	CAMPAIGN("promotion/general"),
	BRAND_CAMPAIGN("promotion/brand"),
	LISTING_PREVIEW("listingPreview"),
	UPLOAD_RESPONSE("uploadResponse"),
	
	ERROR("error"),
	NOT_FOUND("404"),
	
	TEST_PAGE("subsidy/test"),
	SUBSIDY_ACKNOWLEDGMENT("promotion/subsidy_acknowledgment"),
	
	UNKNOW_CAMPAIGN("promotion/unknow");

	private ViewResource(String path) {
		this.path = path;
	}

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
