package com.ebay.raptor.promotion.pojo.business;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public enum DeliveryTime  implements IDescription {
	LT14	("<14 Days"), 
	GE14	(">14 Days");
	
	private String description;
	
	DeliveryTime(String desc) {
		this.description = desc;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
}
