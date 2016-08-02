package com.ebay.raptor.promotion.promo.service;


public enum ViewResource {

	CAMPAIGN("campaign"),
	CAMPAIGN_PREVIEW("campaignPreview"),
	LISTING_PREVIEW("listingPreview"),
	UPLOAD_RESPONSE("uploadResponse"),
	
	HV_APPLICABLE("hotsell/applicable"), 
	HV_APPLIED("hotsell/applied"),
	HV_CANCELLED("hotsell/canceled"),
	HV_END("hotsell/end"),
	HV_DETAIL("hotsell/state"),
	@Deprecated
	HV_AGGREMENT("terms/hotsell"),
	HV_COMPLETED("hotsell/completed"),
	
	DP_APPLICABLE("dealspreset/applicable"),
	DP_APPLIED("dealspreset/applied"),
	DP_CANCELLED("dealspreset/canceled"),
	DP_END("dealspreset/end"),
	DP_DETAIL("dealspreset/state"),
	@Deprecated
	DP_AGGREMENT("terms/dealsPreset"),
	DP_COMPLETED("dealspreset/completed"),
	
	DU_APPLICABLE("deals/applicable"),
	DU_APPLIED("deals/applied"),
	DU_CANCELLED("deals/canceled"),
	DU_END("deals/end"),
	DU_LISTING_PREVIEW("deals/listingPreview"),
	DU_LISTING("deals/listing"),
	DU_DETAIL("deals/state"),
	DU_CONFIRM_LISTING("/promotion/deals/confirmDealsListings"),
	@Deprecated
	DU_AGGREMENT("terms/deals"),
	DU_COMPLETED("deals/completed"),
	@Deprecated
	DU_UPLOAD_RESPONSE("deals/uploadResponse"),
	
	OTHER_CANCELLED("other/canceled"),
	OTHER_END("other/end"),
	OTHER_DETAIL("other/state"),
	@Deprecated
	OTHER_AGGREMENT("terms/other"),
	OTHER_COMPLETED("other/completed"),

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
