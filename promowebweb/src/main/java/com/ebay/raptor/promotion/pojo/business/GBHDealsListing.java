package com.ebay.raptor.promotion.pojo.business;

<<<<<<< Updated upstream
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
=======
import com.ebay.app.raptor.promocommon.excel.header.Header;


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
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
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
=======
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
	
	@Header(title="GBH.skuId", order=2, writable=false, dataType=String.class)
	private String skuId;
	
	@Header(title="GBH.skuName", order=1, writable=false, dataType=String.class)
	private String skuName;
	
	@Header(title="GBH.category", order=3, writable=true, dataType=Enum.class)
	private ProductCategory category;
	
	@Header(title="GBH.itemId", order=4, writable=true, dataType=Long.class)
	private Long itemId;
	
	@Header(title="GBH.listPrice", order=5, writable=true, dataType=Float.class)
	private Float listPrice;
	
	@Header(title="GBH.dealPrice", order=6, writable=true, dataType=Float.class)
	private Float dealPrice;
	
	@Header(title="GBH.qty", order=7, writable=true, dataType=Long.class)
	private Long qty;
	
	@Header(title="GBH.listSite", order=8, writable=true, dataType=Enum.class)
	private Site listSite;
	
	@Header(title="GBH.wwShipOpt", order=9, writable=true, dataType=Enum.class)
	private ShipOption wwShipOpt;
	
	@Header(title="GBH.wwShipChg", order=10, writable=true, dataType=Float.class)
	private Float wwShipChg;
	
	@Header(title="GBH.ruShipOpt", order=11, writable=true, dataType=Enum.class)
	private ShipOption ruShipOpt;
	
	@Header(title="GBH.ruShipChg", order=12, writable=true, dataType=Enum.class)
	private Float ruShipChg;
	
	@Header(title="GBH.cnShipOpt", order=13, writable=true, dataType=Enum.class)
	private ShipOption cnShipOpt;
	
	@Header(title="GBH.cnShipChg", order=14, writable=true, dataType=Float.class)
	private Float cnShipChg;
	
	@Header(title="GBH.laShipOpt", order=15, writable=true, dataType=Enum.class)
	private ShipOption laShipOpt;
	
	@Header(title="GBH.laShipChg", order=16, writable=true, dataType=Float.class)
	private Float laShipChg;
	
	@Header(title="GBH.meShipOpt", order=17, writable=true, dataType=Enum.class)
	private ShipOption meShipOpt;
	
	@Header(title="GBH.meShipChg", order=18, writable=true, dataType=Float.class)
	private Float meShipChg;
	
	@Header(title="GBH.brShipOpt", order=19, writable=true, dataType=Enum.class)
	private ShipOption brShipOpt;
	
	@Header(title="GBH.brShipChg", order=20, writable=true, dataType=Float.class)
	private Float brShipChg;
	
	@Header(title="GBH.isShipOpt", order=21, writable=true, dataType=Enum.class)
	private ShipOption isShipOpt;
	
	@Header(title="GBH.isSHipChg", order=22, writable=true, dataType=Float.class)
	private Float isShipChg;
	
	@Header(title="GBH.currency", order=23, writable=true, dataType=Enum.class)
	private Currency currency;
	
	
	private String state;
>>>>>>> Stashed changes
}
