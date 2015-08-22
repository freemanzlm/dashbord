package com.ebay.raptor.promotion.pojo;

import java.util.Date;

public class Promotion {

	private String name;

	private Integer type;

	private Date promoDlDt;

	private Date promoRwDt;

	private String promoDt;

	private Date rewardRwDt;

	private Date rewardClmDt;

	private Float reward;

	private Integer state;

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

	public Date getPromoRwDt() {
		return promoRwDt;
	}

	public void setPromoRwDt(Date promoRwDt) {
		this.promoRwDt = promoRwDt;
	}

	public String getPromoDt() {
		return promoDt;
	}

	public void setPromoDt(String promoDt) {
		this.promoDt = promoDt;
	}

	public Date getRewardRwDt() {
		return rewardRwDt;
	}

	public void setRewardRwDt(Date rewardRwDt) {
		this.rewardRwDt = rewardRwDt;
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

}
