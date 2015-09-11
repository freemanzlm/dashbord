package com.ebay.raptor.promotion.pojo.service.resp;

public class GeneralDataResponse<T> extends BaseServiceResponse {
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
