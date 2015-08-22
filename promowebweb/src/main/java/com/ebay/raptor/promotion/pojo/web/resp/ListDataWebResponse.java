package com.ebay.raptor.promotion.pojo.web.resp;

import java.util.List;

public class ListDataWebResponse<T> extends BaseWebResponse {

	private List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
