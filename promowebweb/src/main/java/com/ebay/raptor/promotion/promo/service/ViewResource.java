package com.ebay.raptor.promotion.promo.service;

import com.ebay.raptor.promotion.service.ResourceProvider;

public enum ViewResource {

	HV_APPLICABLE("hotsell/applicable"), 
	HV_APPLIED("hotsell/applied"),
	HV_CANCELLED("hotsell/canceled"),
	HV_END("hotsell/end"),
	HV_DETAIL("hotsell/state"),
	
	DP_APPLICABLE("dealspreset/applicable"),
	DP_APPLIED("dealspreset/applied"),
	DP_CANCELLED("dealspreset/canceled"),
	DP_END("dealspreset/end"),
	DP_DETAIL("dealspreset/state"),
	
	DU_APPLICABLE("deals/applicable"),
	DU_APPLIED("deals/applied"),
	DU_CANCELLED("deals/canceled"),
	DU_END("deals/end"),
	DU_LISTING_PREVIEW("deals/listingPreview"),
	DU_LISTING("deals/listing"),
	DU_DETAIL("deals/state"),
	
	DU_CONFIRM_LISTING("/promotion/deals/confirmDealsListings"),
	
	OTHER_CANCELLED("other/canceled"),
	OTHER_END("other/end"),
	OTHER_DETAIL("other/state"),

	ERROR("error");

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
