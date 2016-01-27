package com.ebay.raptor.promotion.pojo.business;

public enum Location implements IDescription{
	UK	("UK"),
	DE	("DE"),
	ESP	("ESP"),
	IT	("IT"),
	FR	("FR"),
	CN	("CN"),
	HK	("HK"),
	TW	("TW"),
	US  ("US");
	
	private String description;
	
	Location(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static Location descriptionOf(String description) {
		if (description == null || description.isEmpty()) {
			return null;
		}
		
		for (Location loc : Location.values()) {
			if (description.equalsIgnoreCase(loc.getDescription())) {
				return loc;
			}
		}
		
		return null;
	}
	
}
