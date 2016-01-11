package com.ebay.raptor.promotion.pojo.business;

import com.ebay.app.raptor.promocommon.excel.header.Header;

public class APACDealsListing {

	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Float getListPrice() {
		return listPrice;
	}
	public void setListPrice(Float listPrice) {
		this.listPrice = listPrice;
	}
	public Float getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(Float dealPrice) {
		this.dealPrice = dealPrice;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public String getRrpLink() {
		return rrpLink;
	}
	public void setRrpLink(String rrpLink) {
		this.rrpLink = rrpLink;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@Header(title="APAC.skuId", order=2, writable=false, dataType=String.class)
	private String skuId;
	
	@Header(title="APAC.skuName", order=1, writable=false, dataType=String.class)
	private String skuName;
	
	@Header(title="APAC.category", order=3, writable=true, dataType=Enum.class)
	private ProductCategory category;
	
	@Header(title="APAC.itemId", order=4, writable=true, dataType=Long.class)
	private Long itemId;
	
	@Header(title="APAC.listPrice", order=6, writable=true, dataType=Float.class)
	private Float listPrice;
	
	@Header(title="APAC.dealPrice", order=5, writable=true, dataType=Float.class)
	private Float dealPrice;
	
	@Header(title="APAC.qty", order=7, writable=true, dataType=Long.class)
	private Long qty;
	
	@Header(title="APAC.rrpLink", order=9, writable=true, dataType=String.class)
	private String rrpLink;
	
	@Header(title="APAC.currency", order=8, writable=true, dataType=Enum.class)
	private Currency currency;
	
	private String state;
}
