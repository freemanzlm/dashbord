package com.ebay.raptor.promotion.pojo.business;

public enum Currency {
	USD	("USD"),
	AUD	("AUD"),
	EURO	("EURO"),
	GBP	("GBP"),
	CAD	("CAD");
	
	private String description;
	
	Currency(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
