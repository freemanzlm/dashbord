package com.ebay.raptor.promotion.security;

public class BackendTokenData {

	public String getAdminUserName() {
		return adminUserName;
	}
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	public String getVisitIp() {
		return visitIp;
	}
	public void setVisitIp(String visitIp) {
		this.visitIp = visitIp;
	}
	public String getVisitDt() {
		return visitDt;
	}
	public void setVisitDt(String visitDt) {
		this.visitDt = visitDt;
	}
	public String getUserVisitIp() {
		return userVisitIp;
	}
	public void setUserVisitIp(String userVisitIp) {
		this.userVisitIp = userVisitIp;
	}

	private String adminUserName;
	private String visitIp;
	private String visitDt;
	private String userVisitIp;
}
