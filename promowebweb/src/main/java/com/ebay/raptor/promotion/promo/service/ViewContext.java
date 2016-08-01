package com.ebay.raptor.promotion.promo.service;

public enum ViewContext {
	
	Promotion(1, "promo"),
	FormURL(2, "formUrl"),
	PromotionId(3, "promoId"),
	ErrorMsg(4, "errorMsg"),
	Agreement(5, "agreement"),
	
	//If user has promotion, then take region from the latest one, 
	//used to identify whether show Subsidy Amount column.
	Region(7, "region"),
	UserName(8, "unm"),
	SDUrl(9, "sdurl"),
	BizUrl(10, "bizurl"),
	TermsAccept(11, "termsAccpted"),
	@Deprecated
	Expired(12, "expired"),
	PromoUpdatedNum(13, "promoUpdatedNum"),
	PromoUpdatedDetail(14, "promoUpdatedDetail"),
	IsAdmin(15, "isAdmin"),
	PromotionSubType(16, "promoSubType"),
	
	IS_PREVIEW(6, "isPreview"),
	IS_NOMINATION_END(17, "isNomitionEnd"),
	HAS_LISTINGS_NOMINATED(18, "hasListingsNominated"),
	// promotion end date is before now
	IS_PROMOTION_STOP(19, "isPromotionStoped"),
	IS_AWARD_END(20, "isAwardEnd"),
	FIELDS_DEFINITIONS(21, "fieldsDefintions"),
	IS_CONFIRM_END(22, "isConfirmEnd"),
	Subsidy(23, "subsidy");
	
	private Integer id;

	private String attr;

	private ViewContext(Integer id, String attr) {
		this.id = id;
		this.attr = attr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

}
