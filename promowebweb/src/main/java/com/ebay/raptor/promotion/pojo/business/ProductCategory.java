package com.ebay.raptor.promotion.pojo.business;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private String description;
}
