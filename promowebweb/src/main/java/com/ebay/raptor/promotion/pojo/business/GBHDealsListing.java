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
	public Float getWwShipChg() {
		return wwShipChg;
	}
	public void setWwShipChg(Float wwShipChg) {
		this.wwShipChg = wwShipChg;
	}
	public ShipOption getRuShipOpt() {
		return ruShipOpt;
	}
	public void setRuShipOpt(ShipOption ruShipOpt) {
		this.ruShipOpt = ruShipOpt;
	}
	public Float getRuShipChg() {
		return ruShipChg;
	}
	public void setRuShipChg(Float ruShipChg) {
		this.ruShipChg = ruShipChg;
	}
	public ShipOption getCnShipOpt() {
		return cnShipOpt;
	}
	public void setCnShipOpt(ShipOption cnShipOpt) {
		this.cnShipOpt = cnShipOpt;
	}
	public Float getCnShipChg() {
		return cnShipChg;
	}
	public void setCnShipChg(Float cnShipChg) {
		this.cnShipChg = cnShipChg;
	}
	public ShipOption getLaShipOpt() {
		return laShipOpt;
	}
	public void setLaShipOpt(ShipOption laShipOpt) {
		this.laShipOpt = laShipOpt;
	}
	public Float getLaShipChg() {
		return laShipChg;
	}
	public void setLaShipChg(Float laShipChg) {
		this.laShipChg = laShipChg;
	}
	public ShipOption getMeShipOpt() {
		return meShipOpt;
	}
	public void setMeShipOpt(ShipOption meShipOpt) {
		this.meShipOpt = meShipOpt;
	}
	public Float getMeShipChg() {
		return meShipChg;
	}
	public void setMeShipChg(Float meShipChg) {
		this.meShipChg = meShipChg;
	}
	public ShipOption getBrShipOpt() {
		return brShipOpt;
	}
	public void setBrShipOpt(ShipOption brShipOpt) {
		this.brShipOpt = brShipOpt;
	}
	public Float getBrShipChg() {
		return brShipChg;
	}
	public void setBrShipChg(Float brShipChg) {
		this.brShipChg = brShipChg;
	}
	public ShipOption getIsShipOpt() {
		return isShipOpt;
	}
	public void setIsShipOpt(ShipOption isShipOpt) {
		this.isShipOpt = isShipOpt;
	}
	public Float getIsShipChg() {
		return isShipChg;
	}
	public void setIsShipChg(Float isShipChg) {
		this.isShipChg = isShipChg;
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
	@Header(title="GBH.skuId", order=0)
	private String skuId;
	@NotEmpty(message = "{listing.skuName.empty}")
	@Header(title="GBH.skuName", order=1)
	private String skuName;
	@NotNull(message = "{listing.category.null}")
	@Header(title="GBH.category", order=2, writable=true)
	private ProductCategory category;
	@NotNull(message = "{listing.itemId.null}")
	@Header(title="GBH.itemId", order=3, writable=true)
	private Long itemId;
	@NotNull(message = "{listing.listPrice.null}")
	@Min(value=0)
	@Header(title="GBH.listPrice", order=4, writable=true)
	private Float listPrice;
	@NotNull(message = "{listing.dealPrice.null}")
	@Min(value=0)
	@Header(title="GBH.dealPrice", order=5, writable=true)
	private Float dealPrice;
	@NotNull(message = "{listing.qty.null}")
	@Min(value=0)
	@Header(title="GBH.qty", order=6, writable=true)
	private Long qty;
	@NotNull(message = "{listing.listSite.null}")
	@Header(title="GBH.listSite", order=7, writable=true)
	private Site listSite;
	@NotNull(message = "{listing.wwShipOpt.null}")
	@Header(title="GBH.wwShipOpt", order=8, writable=true)
	private ShipOption wwShipOpt;
	@Header(title="GBH.wwShipChg", order=9, writable=true)
	private Float wwShipChg;
	@NotNull(message = "{listing.ruShipOpt.null}")
	@Header(title="GBH.ruShipOpt", order=10, writable=true)
	private ShipOption ruShipOpt;
	@Header(title="GBH.ruShipChg", order=11, writable=true)
	private Float ruShipChg;
	@NotNull(message = "{listing.cnShipOpt.null}")
	@Header(title="GBH.cnShipOpt", order=12, writable=true)
	private ShipOption cnShipOpt;
	@Header(title="GBH.cnShipChg", order=13, writable=true)
	private Float cnShipChg;
	@NotNull(message = "{listing.laShipOpt.null}")
	@Header(title="GBH.laShipOpt", order=14, writable=true)
	private ShipOption laShipOpt;
	@Header(title="GBH.laShipChg", order=15, writable=true)
	private Float laShipChg;
	@NotNull(message = "{listing.meShipOpt.null}")
	@Header(title="GBH.meShipOpt", order=16, writable=true)
	private ShipOption meShipOpt;
	@Header(title="GBH.meShipChg", order=17, writable=true)
	private Float meShipChg;
	@NotNull(message = "{listing.brShipOpt.null}")
	@Header(title="GBH.brShipOpt", order=18, writable=true)
	private ShipOption brShipOpt;
	@Header(title="GBH.brShipChg", order=19, writable=true)
	private Float brShipChg;
	@NotNull(message = "{listing.isShipOpt.null}")
	@Header(title="GBH.isShipOpt", order=20, writable=true)
	private ShipOption isShipOpt;
	@Header(title="GBH.isShipChg", order=21, writable=true)
	private Float isShipChg;
	@NotNull(message = "{listing.currency.null}")
	@Header(title="GBH.currency", order=22, writable=true)
	private Currency currency;
	private String state;
}
