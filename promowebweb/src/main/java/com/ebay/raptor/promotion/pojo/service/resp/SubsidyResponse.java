package com.ebay.raptor.promotion.pojo.service.resp;

import javax.ws.rs.core.Response.Status;

import com.ebay.cbt.raptor.promotion.po.Subsidy;
import com.ebay.cbt.raptor.promotion.po.WLTAccount;

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
	private WLTAccount wltAccount;
	
	
	public WLTAccount getWltAccount() {
		return wltAccount;
	}

	public void setWltAccount(WLTAccount wltAccount) {
		this.wltAccount = wltAccount;
	}

	public Subsidy getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(Subsidy subsidy) {
		this.subsidy = subsidy;
	}
}
