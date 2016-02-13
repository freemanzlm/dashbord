package com.ebay.raptor.promotion.pojo.business;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.ebay.app.raptor.promocommon.excel.header.Header;
import com.ebay.raptor.promotion.validation.GBHDealsListingCheck;

@GBHDealsListingCheck()
public class GBHDealsListing {

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
	public Double getListPrice() {
		return listPrice;
	}
	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}
	public Double getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(Double dealPrice) {
		this.dealPrice = dealPrice;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Site getListSite() {
		return listSite;
	}
	public void setListSite(Site listSite) {
		this.listSite = listSite;
	}
	public ShipOption getWwShipOpt() {
		return wwShipOpt;
	}
	public void setWwShipOpt(ShipOption wwShipOpt) {
		this.wwShipOpt = wwShipOpt;
	}
	public Double getWwShipChg() {
		return wwShipChg;
	}
	public void setWwShipChg(Double wwShipChg) {
		this.wwShipChg = wwShipChg;
	}
	public ShipOption getRuShipOpt() {
		return ruShipOpt;
	}
	public void setRuShipOpt(ShipOption ruShipOpt) {
		this.ruShipOpt = ruShipOpt;
	}
	public Double getRuShipChg() {
		return ruShipChg;
	}
	public void setRuShipChg(Double ruShipChg) {
		this.ruShipChg = ruShipChg;
	}
	public ShipOption getCnShipOpt() {
		return cnShipOpt;
	}
	public void setCnShipOpt(ShipOption cnShipOpt) {
		this.cnShipOpt = cnShipOpt;
	}
	public Double getCnShipChg() {
		return cnShipChg;
	}
	public void setCnShipChg(Double cnShipChg) {
		this.cnShipChg = cnShipChg;
	}
	public ShipOption getLaShipOpt() {
		return laShipOpt;
	}
	public void setLaShipOpt(ShipOption laShipOpt) {
		this.laShipOpt = laShipOpt;
	}
	public Double getLaShipChg() {
		return laShipChg;
	}
	public void setLaShipChg(Double laShipChg) {
		this.laShipChg = laShipChg;
	}
	public ShipOption getMeShipOpt() {
		return meShipOpt;
	}
	public void setMeShipOpt(ShipOption meShipOpt) {
		this.meShipOpt = meShipOpt;
	}
	public Double getMeShipChg() {
		return meShipChg;
	}
	public void setMeShipChg(Double meShipChg) {
		this.meShipChg = meShipChg;
	}
	public ShipOption getBrShipOpt() {
		return brShipOpt;
	}
	public void setBrShipOpt(ShipOption brShipOpt) {
		this.brShipOpt = brShipOpt;
	}
	public Double getBrShipChg() {
		return brShipChg;
	}
	public void setBrShipChg(Double brShipChg) {
		this.brShipChg = brShipChg;
	}
	public ShipOption getIsShipOpt() {
		return isShipOpt;
	}
	public void setIsShipOpt(ShipOption isShipOpt) {
		this.isShipOpt = isShipOpt;
	}
	public Double getIsShipChg() {
		return isShipChg;
	}
	public void setIsShipChg(Double isShipChg) {
		this.isShipChg = isShipChg;
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
	@Header(title="GBH.skuId", order=0)
	private String skuId;
	
	@NotEmpty(message = "{listing.skuName.empty}")
	@Header(title="GBH.skuName", order=10)
	private String skuName;
	
	@NotNull(message = "{listing.currency.null}")
	@Header(title="GBH.currency", order=20)
	private String currency;
	
	@NotNull(message = "{listing.category.null}")
	@Header(title="GBH.category", order=30, writable=true)
	private ProductCategory category;
	
	@NotNull(message = "{listing.itemId.null}")
	@Header(title="GBH.itemId", order=40, writable=true)
	private Long itemId;
	
	@NotNull(message = "{listing.listPrice.null}")
	@Min(value=0, message="{listing.listPrice.negative}")
	@Header(title="GBH.listPrice", order=50, writable=true)
	private Double listPrice;
	
	@NotNull(message = "{listing.dealPrice.null}")
	@Min(value=0, message="{listing.dealPrice.negative}")
	@Header(title="GBH.dealPrice", order=60, writable=true)
	private Double dealPrice;
	
	@NotNull(message = "{listing.qty.null}")
	@Min(value=0, message="{listing.qty.negative}")
	@Header(title="GBH.qty", order=70, writable=true)
	private Double qty;
	
	@NotNull(message = "{listing.listSite.null}")
	@Header(title="GBH.listSite", order=80, writable=true)
	private Site listSite;
	
	@NotNull(message = "{listing.wwShipOpt.null}")
	@Header(title="GBH.wwShipOpt", order=90, writable=true)
	private ShipOption wwShipOpt;
	
	@Header(title="GBH.wwShipChg", order=100, writable=true)
	private Double wwShipChg;
	
	@NotNull(message = "{listing.ruShipOpt.null}")
	@Header(title="GBH.ruShipOpt", order=110, writable=true)
	private ShipOption ruShipOpt;
	
	@Header(title="GBH.ruShipChg", order=120, writable=true)
	private Double ruShipChg;
	
	@NotNull(message = "{listing.cnShipOpt.null}")
	@Header(title="GBH.cnShipOpt", order=130, writable=true)
	private ShipOption cnShipOpt;
	
	@Header(title="GBH.cnShipChg", order=140, writable=true)
	private Double cnShipChg;
	
	@NotNull(message = "{listing.laShipOpt.null}")
	@Header(title="GBH.laShipOpt", order=150, writable=true)
	private ShipOption laShipOpt;
	
	@Header(title="GBH.laShipChg", order=160, writable=true)
	private Double laShipChg;
	
	@NotNull(message = "{listing.meShipOpt.null}")
	@Header(title="GBH.meShipOpt", order=170, writable=true)
	private ShipOption meShipOpt;
	
	@Header(title="GBH.meShipChg", order=180, writable=true)
	private Double meShipChg;
	
	@NotNull(message = "{listing.brShipOpt.null}")
	@Header(title="GBH.brShipOpt", order=190, writable=true)
	private ShipOption brShipOpt;
	
	@Header(title="GBH.brShipChg", order=200, writable=true)
	private Double brShipChg;
	
	@NotNull(message = "{listing.isShipOpt.null}")
	@Header(title="GBH.isShipOpt", order=210, writable=true)
	private ShipOption isShipOpt;
	
	@Header(title="GBH.isShipChg", order=220, writable=true)
	private Double isShipChg;

	private Double proposePrice;
	private Double proposeQty;
	private String state;
}
