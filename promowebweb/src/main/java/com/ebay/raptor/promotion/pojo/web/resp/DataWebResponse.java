package com.ebay.raptor.promotion.pojo.web.resp;

public class DataWebResponse<T> extends BaseWebResponse {

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
