package com.ebay.raptor.promotion.enums;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 * 
 * @author lyan2
 */
public enum DeliveryTime  implements ICustomEnum {
	LT14	("<14 Days"), 
	GE14	(">=14 Days");
	
	private String description;
	
	DeliveryTime(String desc) {
		this.description = desc;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

	@JsonCreator
	public static DeliveryTime descriptionOf(String description) {
		if (description == null || description.isEmpty()) {
			return null;
		}
		
		for (DeliveryTime dt : DeliveryTime.values()) {
			if (description.equalsIgnoreCase(dt.getDescription())) {
				return dt;
			}
		}
		
		return null;
	}

	@Override
	public String value() {
		return description;
	}
}
