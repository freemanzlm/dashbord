package com.ebay.raptor.promotion.pojo;

public class RequestParameter {
	
	public String getPromoId() {
		return promoId;
	}
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}
	public String getPromoType() {
		return promoType;
	}
	public void setPromoType(String promoType) {
		this.promoType = promoType;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getPromoSubType() {
		return promoSubType;
	}
	public void setPromoSubType(String promoSubType) {
		this.promoSubType = promoSubType;
	}

	private String promoId;
	private String promoType;
	private String promoSubType;
	private String lang;
}
