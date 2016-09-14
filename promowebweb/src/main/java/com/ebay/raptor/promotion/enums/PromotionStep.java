package com.ebay.raptor.promotion.enums;

public enum PromotionStep {
	DRAFT("Draft", 1),
	NOMINATION_EDM_IN_APPROVE_FLOW("Nomination eDM in approve flow", 2),
	NOMINATION_EDM_APPROVED("Nomination eDM approved", 3),
	SELLER_NOMINATION_NEED_APPROVE("Seller nomination_Need approve", 4),
	PROMOTION_SUBMITTED("Promotion Submitted", 5),
	PROMOTION_APPROVED("Promotion Approved", 6),
	NOTIFICATION_EDM_IN_APPROVE_FLOW("Notification eDM in approve flow", 7),
	NOTIFICATION_EDM_APPROVED("Notification eDM approved", 8),
	SELLER_FEEDBACK("Seller Feedback", 9),
	PROMOTION_IN_PROGRESS("Promotion in progress", 10),
	PROMOTION_END("Promotion end", 11),
	PROMOTION_IN_VALIDATION("Promotion in validation", 12),
	PROMOTION_VALIDATED("Promotion validated", 13);
	
	PromotionStep(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	
	private String name;
	private Integer code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
}
