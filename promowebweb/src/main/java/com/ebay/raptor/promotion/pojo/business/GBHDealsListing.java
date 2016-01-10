package com.ebay.raptor.promotion.pojo.business;

import javax.validation.GroupSequence;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.ebay.app.raptor.promocommon.excel.header.Header;
import com.ebay.raptor.promotion.validation.GBHDealsListingCheck;
import com.ebay.raptor.promotion.validation.group.First;

@GBHDealsListingCheck()
public class GBHDealsListing extends SiteDealsListing {

//	public String getSkuId() {
//		return skuId;
//	}
//	public void setSkuId(String skuId) {
//		this.skuId = skuId;
//	}
//	public String getSkuName() {
//		return skuName;
//	}
//	public void setSkuName(String skuName) {
//		this.skuName = skuName;
//	}
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
//	public Long getItemId() {
//		return itemId;
//	}
//	public void setItemId(Long itemId) {
//		this.itemId = itemId;
//	}
//	public Float getListPrice() {
//		return listPrice;
//	}
//	public void setListPrice(Float listPrice) {
//		this.listPrice = listPrice;
//	}
//	public Float getDealPrice() {
//		return dealPrice;
//	}
//	public void setDealPrice(Float dealPrice) {
//		this.dealPrice = dealPrice;
//	}
//	public Long getQty() {
//		return qty;
//	}
//	public void setQty(Long qty) {
//		this.qty = qty;
//	}
	
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
//	public Currency getCurrency() {
//		return currency;
//	}
//	public void setCurrency(Currency currency) {
//		this.currency = currency;
//	}
//
//	public String getState() {
//		return state;
//	}
//	public void setState(String state) {
//		this.state = state;
//	}

//	@NotEmpty(message = "{listing.skuId.empty}")
//	private String skuId;
//	@NotEmpty(message = "{listing.skuName.empty}")
//	private String skuName;
//	@NotNull(message = "{listing.category.null}")
//	private ProductCategory category;
//	@NotNull(message = "{listing.itemId.null}")
//	private Long itemId;
//	@NotNull(message = "{listing.listPrice.null}")
//	@Min(value=0)
//	private Float listPrice;
//	@NotNull(message = "{listing.dealPrice.null}")
//	@Min(value=0)
//	private Float dealPrice;
//	@NotNull(message = "{listing.qty.null}")
//	@Min(value=0)
//	private Long qty;
	@NotNull(message = "{listing.category.null}")
	@Header(order=2)
	private ProductCategory category;
	@NotNull(message = "{listing.site.null}")
	@Header(order=7)
	private Site listSite;
	@NotNull(message = "{listing.wwShipOpt.null}")
	@Header(order=8)
	private ShipOption wwShipOpt;
	@Header(order=9)
	private Float wwShipChg;
	@NotNull(message = "{listing.ruShipOpt.null}")
	@Header(order=10)
	private ShipOption ruShipOpt;
	@Header(order=11)
	private Float ruShipChg;
	@NotNull(message = "{listing.cnShipOpt.null}")
	@Header(order=12)
	private ShipOption cnShipOpt;
	@Header(order=13)
	private Float cnShipChg;
	@NotNull(message = "{listing.laShipOpt.null}")
	@Header(order=14)
	private ShipOption laShipOpt;
	@Header(order=15)
	private Float laShipChg;
	@NotNull(message = "{listing.meShipOpt.null}")
	@Header(order=16)
	private ShipOption meShipOpt;
	@Header(order=17)
	private Float meShipChg;
	@NotNull(message = "{listing.brShipOpt.null}")
	@Header(order=18)
	private ShipOption brShipOpt;
	@Header(order=19)
	private Float brShipChg;
	@NotNull(message = "{listing.isShipOpt.null}")
	@Header(order=20)
	private ShipOption isShipOpt;
	@Header(order=21)
	private Float isShipChg;
//	@NotNull(message = "{listing.currency.null}")
//	private Currency currency;
//	private String state;
}
