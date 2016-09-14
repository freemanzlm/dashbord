package com.ebay.raptor.promotion.pojo.service.resp;

import javax.ws.rs.core.Response.Status;


public class HasListingNominatedResponse extends BaseServiceResponse {
	public HasListingNominatedResponse() {
		this.setAckValue(AckValue.SUCCESS);
		this.setResponseStatus(Status.OK.getStatusCode());
	}

	private Boolean hasListingNominated;

	public Boolean getHasListingNominated() {
		return hasListingNominated;
	}

	public void setHasListingNominated(Boolean hasListingNominated) {
		this.hasListingNominated = hasListingNominated;
	}

}
