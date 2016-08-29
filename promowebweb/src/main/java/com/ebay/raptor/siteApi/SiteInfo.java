package com.ebay.raptor.siteApi;

/**
 * 
 * @author lyan2
 */
public class SiteInfo {
	public SiteInfo (String edpDevId,
					 String edpAppId,
					 String edpCertId,
					 String ruName,
					 String siteApiUrl,
					 String apiVersion,
					 String siteId,
					 String siteSignInUrl) {
		this.edpDevId = edpDevId;
		this.edpAppId = edpAppId;
		this.edpCertId = edpCertId;
		this.ruName = ruName;
		this.siteApiUrl = siteApiUrl;
		this.apiVersion = apiVersion;
		this.siteId = siteId;
		this.siteSignInUrl = siteSignInUrl;
	}

	public String getEdpDevId() {
		return edpDevId;
	}

	public void setEdpDevId(String edpDevId) {
		this.edpDevId = edpDevId;
	}

	public String getEdpAppId() {
		return edpAppId;
	}

	public void setEdpAppId(String edpAppId) {
		this.edpAppId = edpAppId;
	}

	public String getEdpCertId() {
		return edpCertId;
	}

	public void setEdpCertId(String edpCertId) {
		this.edpCertId = edpCertId;
	}

	public String getRuName() {
		return ruName;
	}

	public void setRuName(String ruName) {
		this.ruName = ruName;
	}

	public String getSiteApiUrl() {
		return siteApiUrl;
	}

	public void setSiteApiUrl(String siteApiUrl) {
		this.siteApiUrl = siteApiUrl;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteSignInUrl() {
		return siteSignInUrl;
	}

	public void setSiteSignInUrl(String siteSignInUrl) {
		this.siteSignInUrl = siteSignInUrl;
	}

	private String edpDevId;
	private String edpAppId;
	private String edpCertId;
	private String ruName;
	private String siteApiUrl;
	private String apiVersion;
	private String siteId;
	private String siteSignInUrl;
}
