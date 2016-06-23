package com.ebay.raptor.promotion.enums;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 * 
 * @author lyan2
 */
public enum ShipOption implements ICustomEnum {
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

	@JsonCreator
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

	@Override
	public String value() {
		return description;
	}
	
}
