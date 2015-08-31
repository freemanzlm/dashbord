package com.ebay.raptor.promotion.list.req;

public class Listing {
	
	public Listing() {
		super();
	}

	private String itemId;

	private Integer selected;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

}