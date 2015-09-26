package com.ebay.raptor.promotion.pojo.service.resp;

import javax.ws.rs.core.Response.Status;


public class PromoAcceptResponse extends BaseServiceResponse {
	public PromoAcceptResponse() {
		this.setAckValue(AckValue.SUCCESS);
		this.setResponseStatus(Status.OK.getStatusCode());
	}

	private Boolean isAccept;

	public Boolean getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(Boolean isAccept) {
		this.isAccept = isAccept;
	}

}
