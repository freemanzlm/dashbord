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
	private Date rewardClmDt;

	private Float reward;
	
	private Integer rewardType;

	private Integer state;

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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
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

}
