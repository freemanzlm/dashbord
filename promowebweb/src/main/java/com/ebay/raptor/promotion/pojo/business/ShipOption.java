package com.ebay.raptor.promotion.pojo.business;

public enum ShipOption implements IDescription{
	Yes	("Yes"),
	No	("No"),
	Free	("Free");
	
	private String description;
	
	ShipOption(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
