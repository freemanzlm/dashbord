package com.ebay.raptor.promotion.pojo.business;

import com.ebay.app.raptor.promocommon.export.ColumnFormat;
import com.ebay.app.raptor.promocommon.export.Header;

public class DealsListing {
	
	private String skuId;

	@Header(name = "招募SKU名称", order=1, format=ColumnFormat.String)
	private String skuName;

	@Header(name = "刊登编号", order=2, format=ColumnFormat.IntNum)
	private Long itemId;

	@Header(name = "刊登名称", order=3, format=ColumnFormat.String)
	private String itemName;

	@Header(name = "刊登当前单价（USD）", order=4, format=ColumnFormat.FltNum)
	private Float price;

	private String currency;

	@Header(name = "刊登刊登库存量", order=6, format=ColumnFormat.IntNum)
	private Float inventory;

	@Header(name = "刊登活动单价（USD）", order=5, format=ColumnFormat.FltNum)
	private Float actPrice;

	private Integer state;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public Float getInventory() {
		return inventory;
	}

	public void setInventory(Float inventory) {
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

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

}
