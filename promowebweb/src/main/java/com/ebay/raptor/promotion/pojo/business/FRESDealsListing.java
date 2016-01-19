package com.ebay.raptor.promotion.pojo.business;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.ebay.app.raptor.promocommon.excel.header.Header;
import com.ebay.raptor.promotion.validation.Link;

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
	
	public Float getProposePrice() {
		return proposePrice;
	}
	public void setProposePrice(Float proposePrice) {
		this.proposePrice = proposePrice;
	}

	public Float getProposeQty() {
		return proposeQty;
	}
	public void setProposeQty(Float proposeQty) {
		this.proposeQty = proposeQty;
	}

	@NotEmpty(message = "{listing.skuId.empty}")
	@Header(title="FRES.skuId", order=0, writable=false)
	private String skuId;
	
	@NotEmpty(message = "{listing.skuName.empty}")
	@Header(title="FRES.skuName", order=1, writable=false)
	private String skuName;
	
	@NotNull(message = "{listing.category.null}")
	@Header(title="FRES.category", order=2, writable=true)
	private ProductCategory category;
	
	@NotNull(message = "{listing.itemId.null}")
	@Header(title="FRES.itemId", order=3, writable=true)
	private Long itemId;
	
	@NotNull(message = "{listing.fvf.null}")
	@Min(value=0, message="{listing.fvf.negative}")
	@Header(title="FRES.fvf", order=4, writable=true)
	private Float fvf;
	
	@NotNull(message = "{listing.listPrice.null}")
	@Min(value=0, message="{listing.listPrice.negative}")
	@Header(title="FRES.listPrice", order=5, writable=true)
	private Float listPrice;
	
	@NotNull(message = "{listing.dealPrice.null}")
	@Min(value=0, message="{listing.dealPrice.negative}")
	@Header(title="FRES.dealPrice", order=6, writable=true)
	private Float dealPrice;
	
	@NotNull(message = "{listing.qty.null}")
	@Min(value=0, message="{listing.qty.negative}")
	@Header(title="FRES.qty", order=7, writable=true)
	private Long qty;
	
	@NotNull(message = "{listing.location.null}")
	@Header(title="FRES.location", order=8, writable=true)
	private Location location;
	
	@NotNull(message = "{listing.dlvyTime.null}")
	@Header(title="FRES.dlvyTime", order=9, writable=true)
	private DeliveryTime dlvyTime;
	
	@NotNull(message = "{listing.shipPrice.null}")
	@Min(value=0, message="{listing.shipPrice.negative}")
	@Header(title="FRES.shipPrice", order=10, writable=true)
	private Float shipPrice;
	
	@NotNull(message = "{listing.currency.null}")
	@Header(title="FRES.currency", order=11, writable=true)
	private Currency currency;
	
	@Link(message = "{listing.url.invalid}")
	@Header(title="FRES.rrpLink", order=12, writable=true)
	private String rrpLink;
	
	private Float proposePrice;
	private Float proposeQty;
	private String state;
}
