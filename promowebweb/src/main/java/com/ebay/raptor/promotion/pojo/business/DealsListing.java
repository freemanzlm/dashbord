package com.ebay.raptor.promotion.pojo.business;

import com.ebay.app.raptor.promocommon.export.ColumnFormat;
import com.ebay.app.raptor.promocommon.export.Header;

public class DealsListing {
	
	@Header(name = "招募SKU编号", order=1, format=ColumnFormat.String)
	private String skuId;

	@Header(name = "招募SKU名称", order=2, format=ColumnFormat.String)
	private String skuName;

	@Header(name = "刊登编号", order=4, format=ColumnFormat.IntNum, writable=true)
	private Long itemId;

//	@Header(name = "刊登名称", order=4, format=ColumnFormat.String, writable=true)
	private String itemTitle;

	@Header(name = "刊登当前单价", order=5, format=ColumnFormat.FltNum, writable=true)
	private Float currPrice;
	
	@Header(name = "刊登活动单价", order=6, format=ColumnFormat.FltNum, writable=true)
	private Float dealsPrice;

	@Header(name = "刊登库存量", order=7, format=ColumnFormat.IntNum, writable=true)
	private Long stockNum;
	
	@Header(name = "备货完成时间(yyyy-MM-dd)", order=8, format=ColumnFormat.Date, writable=true)
	private String stockReadyDate;
	
	@Header(name = "货币单位", order=3, format=ColumnFormat.String)
	private String currency;
	
	private Float proposePrice;

	private String state;

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

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public Float getCurrPrice() {
		return currPrice;
	}

	public void setCurrPrice(Float currPrice) {
		this.currPrice = currPrice;
	}

	public Float getDealsPrice() {
		return dealsPrice;
	}

	public void setDealsPrice(Float dealsPrice) {
		this.dealsPrice = dealsPrice;
	}

	public Long getStockNum() {
		return stockNum;
	}

	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
	}

	public String getStockReadyDate() {
		return stockReadyDate;
	}

	public void setStockReadyDate(String stockReadyDate) {
		this.stockReadyDate = stockReadyDate;
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

	public Float getProposePrice() {
		return proposePrice;
	}

	public void setProposePrice(Float proposePrice) {
		this.proposePrice = proposePrice;
	}
}
