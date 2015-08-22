package com.ebay.raptor.promotion.pojo.service.resp;

import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.common.types.BaseRestResponse;

public abstract class BaseServiceResponse extends BaseRestResponse {

	public static enum AckValue {
		SUCCESS, PARTIAL, FAILURE
	}

	private AckValue ackValue;
	private int responseStatus;

	public AckValue getAckValue() {
		return ackValue;
	}

	public void setAckValue(AckValue ackValue) {
		this.ackValue = ackValue;
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	protected static BaseServiceResponse defaultResponse(BaseServiceResponse resp){
		resp.setAckValue(AckValue.SUCCESS);
		resp.setResponseStatus(Status.OK.getStatusCode());
		return resp;
	}
	
}
