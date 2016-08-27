package com.ebay.raptor.siteApi.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ebay.raptor.siteApi.response.BaseAPIResponse;

/**
 * 
 * @author lyan2
 */
@XmlRootElement(namespace="urn:ebay:apis:eBLBaseComponents", name="FetchTokenResponse")
public class FetchTokenResponse extends BaseAPIResponse {
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="eBayAuthToken")
	public String geteBayAuthToken() {
		return eBayAuthToken;
	}
	public void seteBayAuthToken(String eBayAuthToken) {
		this.eBayAuthToken = eBayAuthToken;
	}
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="HardExpirationTime")
	public String getHardExpirationTime() {
		return hardExpirationTime;
	}
	public void setHardExpirationTime(String hardExpirationTime) {
		this.hardExpirationTime = hardExpirationTime;
	}
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="RESTToken")
	public String getRestToken() {
		return restToken;
	}
	public void setRestToken(String restToken) {
		this.restToken = restToken;
	}

	private String eBayAuthToken;
	private String hardExpirationTime;
	private String restToken;
}
