package com.ebay.raptor.promotion.pojo.business;

public class HotSellListing {

	private String skuId;

	private Long itemId;

	private String name;

	private Double price;

	private String currency;

	private Double volume;

	private Double sales;

	private Double compCost;

	private Double maxComp;

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getSales() {
		return sales;
	}

	public void setSales(Double sales) {
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

	public Double getMaxComp() {
		return maxComp;
	}

	public void setMaxComp(Double maxComp) {
		this.maxComp = maxComp;
	}

	public Double getCompCost() {
		return compCost;
	}

	public void setCompCost(Double compCost) {
		this.compCost = compCost;
	}

}
