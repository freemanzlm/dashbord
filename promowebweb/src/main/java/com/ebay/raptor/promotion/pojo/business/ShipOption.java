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

	public static ShipOption descriptionOf(String description) {
		if (description == null || description.isEmpty()) {
			return null;
		}
		
		for (ShipOption so : ShipOption.values()) {
			if (description.equalsIgnoreCase(so.getDescription())) {
				return so;
			}
		}
		
		return null;
	}
	
}
