package com.ebay.raptor.promotion.pojo.service.resp;

import javax.ws.rs.core.Response.Status;

import com.ebay.cbt.raptor.promotion.po.Promotion;


public class PromotionResponse extends BaseServiceResponse {

	public PromotionResponse() {
		this.setAckValue(AckValue.SUCCESS);
		this.setResponseStatus(Status.OK.getStatusCode());
	}

	private Promotion promotion;

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

}
