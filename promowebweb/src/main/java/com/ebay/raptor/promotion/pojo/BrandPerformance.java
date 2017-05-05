package com.ebay.raptor.promotion.pojo;

import java.util.Date;

/**
 * 
 * @author lyan2
 */
public class BrandPerformance {
	private String name;
	
	private Date lastAuditDt;
	
	private Date nextAuditDt;
	
	private String state;
	
	private Integer defectRateNCompliantAmount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastAuditDt() {
		return lastAuditDt;
	}

	public void setLastAuditDt(Date lastAuditDt) {
		this.lastAuditDt = lastAuditDt;
	}

	public Date getNextAuditDt() {
		return nextAuditDt;
	}

	public void setNextAuditDt(Date nextAuditDt) {
		this.nextAuditDt = nextAuditDt;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getDefectRateNCompliantAmount() {
		return defectRateNCompliantAmount;
	}

	public void setDefectRateNCompliantAmount(Integer defectRateNCompliantAmount) {
		this.defectRateNCompliantAmount = defectRateNCompliantAmount;
	}

}
