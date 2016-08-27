package com.ebay.raptor.siteApi.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author lyan2
 */
@XmlRootElement(namespace="urn:ebay:apis:eBLBaseComponents", name="GetSessionIDResponse")
public class GetSessionIDResponse extends BaseAPIResponse{
	
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="SessionID")
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	private String sessionID;
}
