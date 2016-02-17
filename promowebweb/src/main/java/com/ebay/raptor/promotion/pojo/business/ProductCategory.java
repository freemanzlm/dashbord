package com.ebay.raptor.promotion.pojo.business;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public enum ProductCategory  implements IDescription {
	BAI 		("Business & Industrial"),
	Collect		("Collectibles"),
	Elect		("Electronics"),
	Fashion		("Fashion"),
	HAG			("Home & Garden"),
	LifeStyle	("Lifestyle"),
	Media		("Media");
	
	ProductCategory (String desc) {
		this.description = desc;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private String description;

	@JsonCreator
	public static ProductCategory descriptionOf(String description) {
		if (description == null || description.isEmpty()) {
			return null;
		}
		
		for (ProductCategory pc : ProductCategory.values()) {
			if (description.equalsIgnoreCase(pc.getDescription())) {
				return pc;
			}
		}
		
		return null;
	}
}
