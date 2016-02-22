package com.ebay.raptor.promotion.pojo.business;

public enum Site implements IDescription{
	
	US	("US"),
	CA	("CA"),
	UK	("UK"),
	ESP	("ESP"),
	IT	("IT"),
	DE	("DE"),
	FR	("FR"),
	AU	("AU");
	
	private String description;
	
	Site(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static Site descriptionOf(String description) {
		if (description == null || description.isEmpty()) {
			return null;
		}
		
		for (Site site : Site.values()) {
			if (description.equalsIgnoreCase(site.getDescription())) {
				return site;
			}
		}
		
		return null;
	}
}