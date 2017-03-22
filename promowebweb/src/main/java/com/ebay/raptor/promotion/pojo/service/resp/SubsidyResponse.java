package com.ebay.raptor.promotion.pojo.service.resp;

import javax.ws.rs.core.Response.Status;

import com.ebay.raptor.promotion.pojo.business.Subsidy;

/**
 * 
 * @author lyan2
 */
public class SubsidyResponse extends BaseServiceResponse {
	public SubsidyResponse() {
		this.setAckValue(AckValue.SUCCESS);
		this.setResponseStatus(Status.OK.getStatusCode());
	}

	private Subsidy subsidy;

	public Subsidy getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(Subsidy subsidy) {
		this.subsidy = subsidy;
	}
}
