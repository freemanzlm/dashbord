package com.ebay.raptor.promotion.pojo.service.resp;

import java.util.List;

import javax.ws.rs.core.Response.Status;


public class UploadListingResponse<T> extends BaseServiceResponse {

	public UploadListingResponse() {
		this.setAckValue(AckValue.SUCCESS);
		this.setResponseStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}

	private List<T> successListings;

	private List<T> failedListings;

	public List<T> getSuccessListings() {
		return successListings;
	}

	public void setSuccessListings(List<T> successListings) {
		this.successListings = successListings;
	}

	public List<T> getFailedListings() {
		return failedListings;
	}

	public void setFailedListings(List<T> failedListings) {
		this.failedListings = failedListings;
	}

}
