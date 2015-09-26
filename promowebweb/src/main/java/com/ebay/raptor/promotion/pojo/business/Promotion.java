package com.ebay.raptor.promotion.pojo.business;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ebay.raptor.promotion.util.WebDateSerializer;

public class Promotion {

	private String promoId;
	
	private String name;
	
	private String desc;

	private Integer type;

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
	
	private Integer rewardType;
	
	private String rewardUrl;

	private String state;
	
	private Boolean regEnded;
	
	@JsonSerialize(using=WebDateSerializer.class)
	private Date regEndDate;
	
	@JsonSerialize(using=WebDateSerializer.class)
	private Date uploadDeadline;
	
	private String region;
	
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

}
