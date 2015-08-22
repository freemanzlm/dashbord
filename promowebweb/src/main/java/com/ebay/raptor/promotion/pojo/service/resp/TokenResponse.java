package com.ebay.raptor.promotion.pojo.service.resp;


public class TokenResponse extends BaseServiceResponse{
	
	private String iafToken = "";

	public String getIafToken() {
		return iafToken;
	}

	public void setIafToken(String iafToken) {
		this.iafToken = iafToken;
	}
	
}
