package com.ebay.raptor.promotion.pojo.business;

import com.ebay.app.raptor.promocommon.excel.header.Header;

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
	
	@Header(title="FRES.skuId", order=2, writable=false, dataType=String.class)
	private String skuId;
	
	@Header(title="FRES.skuName", order=1, writable=false, dataType=String.class)
	private String skuName;
	
	@Header(title="FRES.category", order=3, writable=true, dataType=Enum.class)
	private ProductCategory category;
	
	@Header(title="FRES.itemId", order=4, writable=true, dataType=Long.class)
	private Long itemId;
	
	@Header(title="FRES.esItemId", order=5, writable=true, dataType=Long.class)
	private Long esItemId;
	
	@Header(title="FRES.fvf", order=6, writable=true, dataType=Float.class)
	private Float fvf;
	
	@Header(title="FRES.listPrice", order=7, writable=true, dataType=Float.class)
	private Float listPrice;
	
	@Header(title="FRES.dealPrice", order=8, writable=true, dataType=Float.class)
	private Float dealPrice;
	
	@Header(title="FRES.qty", order=9, writable=true, dataType=Long.class)
	private Long qty;
	
	@Header(title="FRES.location", order=10, writable=true, dataType=Enum.class)
	private Location location;
	
	@Header(title="FRES.dlvyTime", order=11, writable=true, dataType=Enum.class)
	private DeliveryTime dlvyTime;
	
	@Header(title="FRES.shipPrice", order=12, writable=true, dataType=Float.class)
	private Float shipPrice;
	
	@Header(title="FRES.rrpLink", order=13, writable=true, dataType=String.class)
	private String rrpLink;
	
	@Header(title="FRES.currency", order=14, writable=true, dataType=Enum.class)
	private Currency currency;
	
	private String state;
}
