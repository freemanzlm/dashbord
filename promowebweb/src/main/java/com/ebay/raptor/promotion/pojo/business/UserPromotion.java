package com.ebay.raptor.promotion.pojo.business;

public class UserPromotion {

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPromoName() {
		return promoName;
	}
	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}
	public String getPromoId() {
		return promoId;
	}
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}
	public PromotionType getPromotionType() {
		return promoType;
	}
	public void setPromotionType(PromotionType promoType) {
		this.promoType = promoType;
	}
	public PromotionStatus getPromoStatus() {
		return promoStatus;
	}
	public void setPromoStatus(PromotionStatus promoStatus) {
		this.promoStatus = promoStatus;
	}
	public String getPromoDescription() {
		return promoDescription;
	}
	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}
	public String getPromoEnrollDeadline() {
		return promoEnrollDeadline;
	}
	public void setPromoEnrollDeadline(String promoEnrollDeadline) {
		this.promoEnrollDeadline = promoEnrollDeadline;
	}
	public String getPromoStartDate() {
		return promoStartDate;
	}
	public void setPromoStartDate(String promoStartDate) {
		this.promoStartDate = promoStartDate;
	}
	public String getPromoEndDate() {
		return promoEndDate;
	}
	public void setPromoEndDate(String promoEndDate) {
		this.promoEndDate = promoEndDate;
	}

	public Boolean getPromoResult() {
		return promoResult;
	}
	public void setPromoResult(Boolean promoResult) {
		this.promoResult = promoResult;
	}

	private String userName;
	private Long userId;
	private String promoName;
	private String promoId;
	private PromotionType promoType;
	private PromotionStatus promoStatus;
	private Boolean promoResult;
	private String promoDescription;
	private String promoEnrollDeadline;
	private String promoStartDate;
	private String promoEndDate;
}
