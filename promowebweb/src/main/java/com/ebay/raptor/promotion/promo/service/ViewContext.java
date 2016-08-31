package com.ebay.raptor.promotion.promo.service;

public enum ViewContext {
	
	Promotion(1, "promo"),
	FormURL(2, "formUrl"),
	PromotionId(3, "promoId"),
	ErrorMsg(4, "errorMsg"),
	Agreement(5, "agreement"),
	IsUnconfirmedVisable(6, "invisible"),
	//If user has promotion, then take region from the latest one, 
	//used to identify whether show Subsidy Amount column.
	Region(7, "region"),
	UserName(8, "unm"),
	SDUrl(9, "sdurl"),
	BizUrl(10, "bizurl"),
	TermsAccept(11, "termsAccpted"),
	Expired(12, "expired"),
	PromoUpdatedNum(13, "promoUpdatedNum"),
	PromoUpdatedDetail(14, "promoUpdatedDetail"),
	IsAdmin(15, "isAdmin"),
	PromotionSubType(16, "promoSubType"),
	UserId(17, "userId");
	
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
