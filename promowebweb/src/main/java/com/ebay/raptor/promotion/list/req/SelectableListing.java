package com.ebay.raptor.promotion.list.req;

public class SelectableListing {
	
	public SelectableListing() {
		super();
	}

	private String skuId;

	private Integer selected;

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

}