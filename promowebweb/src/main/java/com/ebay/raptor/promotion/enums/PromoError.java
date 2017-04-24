package com.ebay.raptor.promotion.enums;

public enum PromoError {
	PROMOTION_NOT_FOUND("PROMOTION_NOT_FOUND"),
	SUBSIDY_NOT_FOUND("SUBSIDY_NOT_FOUND"),
	SUBSIDY_LEGALTERM_NOT_FOUND("SUBSIDY_LEGALTERM_NOT_FOUND"),
	SUBSIDY_SUBMISSION_NOT_FOUND("SUBSIDY_SUBMISSION_NOT_FOUND");
	
	PromoError(String key) {
		this.key = key;
	}
	
	private String key;
	public String getKey() {
		return key;
	}	
	
}
