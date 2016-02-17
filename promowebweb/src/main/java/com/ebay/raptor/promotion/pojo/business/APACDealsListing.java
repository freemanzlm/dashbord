package com.ebay.raptor.promotion.pojo.business;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.ebay.app.raptor.promocommon.excel.header.Header;
import com.ebay.raptor.promotion.validation.Link;

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
	public Double getCurrPrice() {
		return currPrice;
	}
	public void setCurrPrice(Double currPrice) {
		this.currPrice = currPrice;
	}
	public Double getDealsPrice() {
		return dealsPrice;
	}
	public void setDealsPrice(Double dealsPrice) {
		this.dealsPrice = dealsPrice;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getRrpLink() {
		return rrpLink;
	}
	public void setRrpLink(String rrpLink) {
		this.rrpLink = rrpLink;
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

	public Double getProposeQty() {
		return proposeQty;
	}
	public void setProposeQty(Double proposeQty) {
		this.proposeQty = proposeQty;
	}

	public Double getProposePrice() {
		return proposePrice;
	}
	public void setProposePrice(Double proposePrice) {
		this.proposePrice = proposePrice;
	}

	@NotEmpty(message = "{listing.skuId.empty}")
	@Header(title="APAC.skuId", order=0, writable=false)
	private String skuId;
	
	@NotEmpty(message = "{listing.skuName.empty}")
	@Header(title="APAC.skuName", order=10, writable=false)
	private String skuName;
	
	@NotNull(message = "{listing.currency.null}")
	@Header(title="APAC.currency", order=20, writable=false)
	private String currency;
	
	@NotNull(message = "{listing.category.null}")
	@Header(title="APAC.category", order=30, writable=true)
	private ProductCategory category;
	
	@NotNull(message = "{listing.itemId.null}")
	@Header(title="APAC.itemId", order=40, writable=true)
	private Long itemId;
	
	@NotNull(message = "{listing.currPrice.null}")
	@Min(value=0, message="{listing.currPrice.negative}")
	@Header(title="APAC.currPrice", order=50, writable=true)
	private Double currPrice;
	
	@NotNull(message = "{listing.dealsPrice.null}")
	@Min(value=0, message="{listing.dealsPrice.negative}")
	@Header(title="APAC.dealsPrice", order=60, writable=true)
	private Double dealsPrice;
	
	@NotNull(message = "{listing.qty.null}")
	@Min(value=0, message="{listing.qty.negative}")
	@Header(title="APAC.qty", order=70, writable=true)
	private Double qty;

	@Link(message = "{listing.url.invalid}")
	@Header(title="APAC.rrpLink", order=80, writable=true)
	private String rrpLink;
	
	private Double proposePrice;
	private Double proposeQty;
	private String state;
}
