package com.ebay.raptor.promotion.pojo.business;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ebay.raptor.promotion.util.WebDateSerializer;

public class Promotion {

	private String promoId;
	
	private String name;
	
	private String desc;
	
	private String itemDesc;
	
	private Boolean regType = true;

	private Integer type;
	
	private String currentStep;
	
	private String adjustedCurrentStep;
	
	private Boolean hasValidCurrentStep;
	
	private String stepList;

	@JsonSerialize(using=WebDateSerializer.class)
	private Date promoDlDt;

	@JsonSerialize(using=WebDateSerializer.class)
	private Date promoSdt;

	@JsonSerialize(using=WebDateSerializer.class)
	private Date promoEdt;

	@JsonSerialize(using=WebDateSerializer.class)
	private Date rewardDlDt;
	
	@JsonSerialize(using=WebDateSerializer.class)
	private Date rewardClmDt;

	private Float reward;
	
	private String currency;
	
	private Integer rewardType;
	
	private String rewardUrl;

	private String state;
	
	private Boolean regEnded;
	
	@JsonSerialize(using=WebDateSerializer.class)
	private Date regEndDate;
	
	@JsonSerialize(using=WebDateSerializer.class)
	private Date uploadDeadline;
	
	private String region;
	
	private Boolean isReversed;
	
	private Boolean isEnded;
	
	private String endReason;
	
	/**
	 * Listing fields definition. It's a JSON object string.
	 */
	private String listingFields;
	
	public String getListingFields() {
		return listingFields;
	}

	public void setListingFields(String listingFields) {
		this.listingFields = listingFields;
	}

	public Boolean getIsEnded() {
		return isEnded;
	}

	public void setIsEnded(Boolean isEnded) {
		this.isEnded = isEnded;
	}

	public String getEndReason() {
		return endReason;
	}

	public void setEndReason(String endReason) {
		this.endReason = endReason;
	}

	public Boolean getIsReversed() {
		return isReversed;
	}

	public void setIsReversed(Boolean isReversed) {
		this.isReversed = isReversed;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Date getUploadDeadline() {
		return uploadDeadline;
	}

	public void setUploadDeadline(Date uploadDeadline) {
		this.uploadDeadline = uploadDeadline;
	}

	public Date getRewardDlDt() {
		return rewardDlDt;
	}

	public void setRewardDlDt(Date rewardDlDt) {
		this.rewardDlDt = rewardDlDt;
	}

	public Date getRegEndDate() {
		return regEndDate;
	}

	public void setRegEndDate(Date regEndDate) {
		this.regEndDate = regEndDate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getPromoDlDt() {
		return promoDlDt;
	}

	public void setPromoDlDt(Date promoDlDt) {
		this.promoDlDt = promoDlDt;
	}

	public Date getPromoSdt() {
		return promoSdt;
	}

	public void setPromoSdt(Date promoSdt) {
		this.promoSdt = promoSdt;
	}

	public Date getPromoEdt() {
		return promoEdt;
	}

	public void setPromoEdt(Date promoEdt) {
		this.promoEdt = promoEdt;
	}

	public Date getRewardClmDt() {
		return rewardClmDt;
	}

	public void setRewardClmDt(Date rewardClmDt) {
		this.rewardClmDt = rewardClmDt;
	}

	public Float getReward() {
		return reward;
	}

	public void setReward(Float reward) {
		this.reward = reward;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPromoId() {
		return promoId;
	}

	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}

	public Integer getRewardType() {
		return rewardType;
	}

	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}

	public Boolean getRegEnded() {
		return regEnded;
	}

	public void setRegEnded(Boolean regEnded) {
		this.regEnded = regEnded;
	}

	public String getRewardUrl() {
		return rewardUrl;
	}

	public void setRewardUrl(String rewardUrl) {
		this.rewardUrl = rewardUrl;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	public String getStepList() {
		return stepList;
	}

	public void setStepList(String stepList) {
		this.stepList = stepList;
	}

	public Boolean getHasValidCurrentStep() {
		return hasValidCurrentStep;
	}

	public void setHasValidCurrentStep(Boolean hasValidCurrentStep) {
		this.hasValidCurrentStep = hasValidCurrentStep;
	}

	public String getAdjustedCurrentStep() {
		return adjustedCurrentStep;
	}

	public void setAdjustedCurrentStep(String adjustedCurrentStep) {
		this.adjustedCurrentStep = adjustedCurrentStep;
	}

	public Boolean getRegType() {
		return regType;
	}

	public void setRegType(Boolean regType) {
		this.regType = regType;
	}

}
