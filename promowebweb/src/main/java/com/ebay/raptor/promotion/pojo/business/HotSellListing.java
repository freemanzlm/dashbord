package com.ebay.raptor.promotion.pojo.business;

public class HotSellListing {

	private String skuId;

	private Long itemId;

	private String name;

	private Float price;

	private String currency;

	private Float volume;

	private Float sales;

	private Float compCast;

	private Float maxComp;

	private String state;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	public Float getSales() {
		return sales;
	}

	public void setSales(Float sales) {
		this.sales = sales;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Float getCompCast() {
		return compCast;
	}

	public void setCompCast(Float compCast) {
		this.compCast = compCast;
	}

	public Float getMaxComp() {
		return maxComp;
	}

	public void setMaxComp(Float maxComp) {
		this.maxComp = maxComp;
	}

}
