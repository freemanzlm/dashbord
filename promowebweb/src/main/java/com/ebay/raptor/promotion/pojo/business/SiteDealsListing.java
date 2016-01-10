package com.ebay.raptor.promotion.pojo.business;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.ebay.app.raptor.promocommon.excel.header.Header;

public class SiteDealsListing {

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

	@NotEmpty(message = "{listing.skuId.empty}")
	@Header(order=0)
	private String skuId;
	@NotEmpty(message = "{listing.skuName.empty}")
	@Header(order=1)
	private String skuName;
	@NotNull(message = "{listing.itemId.null}")
	@Header(order=3)
	private Long itemId;
	@NotNull(message = "{listing.listPrice.null}")
	@Min(value=0)
	@Header(order=4)
	private Float listPrice;
	@NotNull(message = "{listing.dealPrice.null}")
	@Min(value=0)
	@Header(order=5)
	private Float dealPrice;
	@NotNull(message = "{listing.qty.null}")
	@Min(value=0)
	@Header(order=6)
	private Long qty;
	@NotNull(message = "{listing.currency.null}")
	@Header(order=22)
	private Currency currency;
	private String state;
}
