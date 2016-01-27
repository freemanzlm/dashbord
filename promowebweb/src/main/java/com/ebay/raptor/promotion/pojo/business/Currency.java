package com.ebay.raptor.promotion.pojo.business;

public enum Currency implements IDescription {
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

	public static Currency descriptionOf(String description) {
		if (description == null || description.isEmpty()) {
			return null;
		}
		
		for (Currency curr : Currency.values()) {
			if (description.equalsIgnoreCase(curr.getDescription())) {
				return curr;
			}
		}
		
		return null;
	}
	
}
