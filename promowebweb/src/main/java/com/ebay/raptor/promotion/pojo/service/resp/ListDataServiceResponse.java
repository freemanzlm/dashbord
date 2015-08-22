package com.ebay.raptor.promotion.pojo.service.resp;

import java.util.List;


public class ListDataServiceResponse<T> extends BaseServiceResponse {

	private List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
