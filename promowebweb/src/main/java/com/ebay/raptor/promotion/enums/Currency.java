package com.ebay.raptor.promotion.enums;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 * @author lyan2
 */
public enum Currency implements ICustomEnum {
	USD	("USD"),
	AUD	("AUD"),
	EURO("EURO"),
	GBP	("GBP"),
	CAD	("CAD");
	
	private String description;
	
	Currency(String desc) {
		this.description = desc;
	}
	
	@Override
	public String value() {
		return description;
	}

	public String getDescription() {
		return description;
	}
	
	@JsonCreator
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