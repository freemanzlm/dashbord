package com.ebay.raptor.promotion.pojo.business;

public class FRESDealsListing {

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
	public Long getEsItemId() {
		return esItemId;
	}
	public void setEsItemId(Long esItemId) {
		this.esItemId = esItemId;
	}
	public Float getFvf() {
		return fvf;
	}
	public void setFvf(Float fvf) {
		this.fvf = fvf;
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
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public DeliveryTime getDlvyTime() {
		return dlvyTime;
	}
	public void setDlvyTime(DeliveryTime dlvyTime) {
		this.dlvyTime = dlvyTime;
	}
	public Float getShipPrice() {
		return shipPrice;
	}
	public void setShipPrice(Float shipPrice) {
		this.shipPrice = shipPrice;
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

	private String skuId;
	private String skuName;
	private ProductCategory category;
	private Long itemId;
	private Long esItemId;
	private Float fvf;
	private Float listPrice;
	private Float dealPrice;
	private Long qty;
	private Location location;
	private DeliveryTime dlvyTime;
	private Float shipPrice;
	private String rrpLink;
	private Currency currency;
	private String state;
}
