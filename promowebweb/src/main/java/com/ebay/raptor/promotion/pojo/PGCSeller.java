package com.ebay.raptor.promotion.pojo;

/**
 * 
 * @author binkang
 */
public class PGCSeller {

	public Integer getRemainingQuota() {
		return remainingQuota;
	}
	public void setRemainingQuota(Integer remainingQuota) {
		this.remainingQuota = remainingQuota;
	}
	public String getLimitEligibility() {
		return limitEligibility;
	}
	public void setLimitEligibility(String limitEligibility) {
		this.limitEligibility = limitEligibility;
	}
	public Integer getLimitQty() {
		return limitQty;
	}
	public void setLimitQty(Integer limitQty) {
		this.limitQty = limitQty;
	}
	
	public boolean isPgcEligibility() {
		return pgcEligibility;
	}
	public void setPgcEligibility(boolean pgcEligibility) {
		this.pgcEligibility = pgcEligibility;
	}

	private Integer remainingQuota;
	private String limitEligibility;
	private Integer limitQty;
	private boolean pgcEligibility;
	
}
