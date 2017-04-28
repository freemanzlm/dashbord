package com.ebay.raptor.promotion.pojo;

import java.util.Set;

/**
 * @author binkang
 * @date Mar 31, 2017
 */
public class PgcEligiblity {
	
	private Boolean pgcEligibility;
	private Set<String> pgcStatusCode;
	
	public Boolean getPgcEligibility() {
		return pgcEligibility;
	}
	public void setPgcEligibility(Boolean pgcEligibility) {
		this.pgcEligibility = pgcEligibility;
	}
	
	public Set<String> getPgcStatusCode() {
		return pgcStatusCode;
	}
	
	public void setPgcStatusCode(Set<String> pgcStatusCode) {
		this.pgcStatusCode = pgcStatusCode;
	}

}
