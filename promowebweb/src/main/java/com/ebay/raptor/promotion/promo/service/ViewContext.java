package com.ebay.raptor.promotion.promo.service;

public enum ViewContext {

	Promotion(1, "promo"),
	FormURL(2, "formUrl"),
	PromotionId(3, "promoId"),
	ErrorMsg(4, "errorMsg");
	
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
