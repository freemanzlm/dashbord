package com.ebay.raptor.promotion.pojo.business;

public class DealsListing {

	private Long itemId;

	private String name;

	private Float price;

	private String currency;

	private Integer inventory;

	private Float actPrice;

	private Integer state;

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

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public Float getActPrice() {
		return actPrice;
	}

	public void setActPrice(Float actPrice) {
		this.actPrice = actPrice;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
