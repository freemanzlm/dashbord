package com.ebay.raptor.promotion.pojo.business;

public enum DeliveryTime {
	LT14	("小于14Days"), 
	GE14	("大于等于 14Days");
	
	private String description;
	
	DeliveryTime(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
