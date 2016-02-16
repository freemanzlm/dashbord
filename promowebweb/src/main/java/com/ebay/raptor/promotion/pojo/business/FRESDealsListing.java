package com.ebay.raptor.promotion.pojo.business;

import javax.validation.constraints.Max;
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
	public Double getFvf() {
		return fvf;
	}
	public void setFvf(Double fvf) {
		this.fvf = fvf;
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
	public Double getShipPrice() {
		return shipPrice;
	}
	public void setShipPrice(Double shipPrice) {
		this.shipPrice = shipPrice;
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
	
	public Double getProposePrice() {
		return proposePrice;
	}
	public void setProposePrice(Double proposePrice) {
		this.proposePrice = proposePrice;
	}

	public Double getProposeQty() {
		return proposeQty;
	}
	public void setProposeQty(Double proposeQty) {
		this.proposeQty = proposeQty;
	}

	@NotEmpty(message = "{listing.skuId.empty}")
	@Header(title="FRES.skuId", order=0, writable=false)
	private String skuId;
	
	@NotEmpty(message = "{listing.skuName.empty}")
	@Header(title="FRES.skuName", order=10, writable=false)
	private String skuName;
	
	@NotNull(message = "{listing.currency.null}")
	@Header(title="FRES.currency", order=20, writable=false)
	private String currency;
	
	@NotNull(message = "{listing.category.null}")
	@Header(title="FRES.category", order=30, writable=true)
	private ProductCategory category;
	
	@NotNull(message = "{listing.itemId.null}")
	@Header(title="FRES.itemId", order=40, writable=true)
	private Long itemId;
	
	@NotNull(message = "{listing.fvf.null}")
	@Min(value=0, message="{listing.fvf.negative}")
	@Max(value=100, message="{listing.fvf.toolarge}")
	@Header(title="FRES.fvf", order=50, writable=true)
	private Double fvf;
	
	@NotNull(message = "{listing.currPrice.null}")
	@Min(value=0, message="{listing.currPrice.negative}")
	@Header(title="FRES.currPrice", order=60, writable=true)
	private Double currPrice;
	
	@NotNull(message = "{listing.dealsPrice.null}")
	@Min(value=0, message="{listing.dealsPrice.negative}")
	@Header(title="FRES.dealsPrice", order=70, writable=true)
	private Double dealsPrice;
	
	@NotNull(message = "{listing.qty.null}")
	@Min(value=0, message="{listing.qty.negative}")
	@Header(title="FRES.qty", order=80, writable=true)
	private Double qty;
	
	@NotNull(message = "{listing.location.null}")
	@Header(title="FRES.location", order=90, writable=true)
	private Location location;
	
	@NotNull(message = "{listing.dlvyTime.null}")
	@Header(title="FRES.dlvyTime", order=100, writable=true)
	private DeliveryTime dlvyTime;
	
	@NotNull(message = "{listing.shipPrice.null}")
	@Min(value=0, message="{listing.shipPrice.negative}")
	@Header(title="FRES.shipPrice", order=110, writable=true)
	private Double shipPrice;

	@Link(message = "{listing.url.invalid}")
	@Header(title="FRES.rrpLink", order=120, writable=true)
	private String rrpLink;
	
	private Double proposePrice;
	private Double proposeQty;
	private String state;
}
