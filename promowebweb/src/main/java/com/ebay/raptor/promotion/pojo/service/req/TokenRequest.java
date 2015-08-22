package com.ebay.raptor.promotion.pojo.service.req;

public class TokenRequest {

	private String consumerId;

	private String secret;

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
