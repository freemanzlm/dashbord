package com.ebay.raptor.promotion.pojo.business;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

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

	@NotEmpty(message = "{listing.skuId.empty}")
	@Header(title="APAC.skuId", order=0, writable=false)
	private String skuId;
	
	@NotEmpty(message = "{listing.skuName.empty}")
	@Header(title="APAC.skuName", order=1, writable=false)
	private String skuName;
	
	@NotNull(message = "{listing.category.null}")
	@Header(title="APAC.category", order=2, writable=true)
	private ProductCategory category;
	
	@NotNull(message = "{listing.itemId.null}")
	@Header(title="APAC.itemId", order=3, writable=true)
	private Long itemId;
	
	@NotNull(message = "{listing.listPrice.null}")
	@Min(value=0)
	@Header(title="APAC.listPrice", order=4, writable=true)
	private Float listPrice;
	
	@NotNull(message = "{listing.dealPrice.null}")
	@Min(value=0)
	@Header(title="APAC.dealPrice", order=5, writable=true)
	private Float dealPrice;
	
	@NotNull(message = "{listing.qty.null}")
	@Min(value=0)
	@Header(title="APAC.qty", order=6, writable=true)
	private Long qty;
	
	@NotNull(message = "{listing.currency.null}")
	@Header(title="APAC.currency", order=7, writable=true)
	private Currency currency;
	
	@NotEmpty(message = "{listing.rrpLink.empty}")
	@Header(title="APAC.rrpLink", order=8, writable=true)
	private String rrpLink;
	
	private String state;
}
