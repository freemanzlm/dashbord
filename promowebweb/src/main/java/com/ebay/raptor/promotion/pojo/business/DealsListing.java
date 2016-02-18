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
	private Double currPrice;
	
	@Header(name = "刊登活动单价", order=6, format=ColumnFormat.FltNum, writable=true)
	private Double dealsPrice;

	@Header(name = "刊登库存量", order=7, format=ColumnFormat.IntNum, writable=true)
	private Double stockNum;
	
	@Header(name = "备货完成时间(YYYY-MM-DD)", order=8, format=ColumnFormat.Date, writable=true)
	private String stockReadyDate;
	
	@Header(name = "货币单位", order=3, format=ColumnFormat.String)
	private String currency;
	
	private Double proposePrice;

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

	public Double getStockNum() {
		return stockNum;
	}

	public void setStockNum(Double stockNum) {
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

	public Double getProposePrice() {
		return proposePrice;
	}

	public void setProposePrice(Double proposePrice) {
		this.proposePrice = proposePrice;
	}
}
