package com.ebay.raptor.promotion.pojo.business;

/**
 * Each Listing has skuId, skuTitle. Promotion fields are stored in nominationValues. "nominationValues" is a JSON object string.
 * 
 * @author lyan2
 */
public class Listing {

	/**
	 *  This is not the real sku id, in fact, it's nomination id.
	 */
	private String skuId;
	
	private String currency;
	
	/**
	 * This is a JSON object string. These fields are configured by Promotion.getListingFields().
	 */
	private String nominationValues;
	
	private String state;
	
	private Boolean hasUploaded;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNominationValues() {
		return nominationValues;
	}

	public void setNominationValues(String nominationValues) {
		this.nominationValues = nominationValues;
	}

	public Boolean getHasUploaded() {
		return hasUploaded;
	}

	public void setHasUploaded(Boolean hasUploaded) {
		this.hasUploaded = hasUploaded;
	}
}
