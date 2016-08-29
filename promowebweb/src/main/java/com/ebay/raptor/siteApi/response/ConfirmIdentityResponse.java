package com.ebay.raptor.siteApi.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author lyan2
 */
@XmlRootElement(namespace="urn:ebay:apis:eBLBaseComponents", name="ConfirmIdentityResponse")
public class ConfirmIdentityResponse extends BaseAPIResponse {
	
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="UserID")
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	private String userID;
}
