package com.ebay.raptor.promotion.enums;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 * 
 * @author lyan2
 */
public enum Site implements ICustomEnum {
	
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

	@Override
	public String value() {
		return description;
	}
	
	@JsonCreator
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
