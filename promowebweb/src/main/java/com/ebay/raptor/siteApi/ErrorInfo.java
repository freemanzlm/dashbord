package com.ebay.raptor.siteApi;

import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author lyan2
 */

public class ErrorInfo {
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="ErrorClassification")
	public String getErrorClassification() {
		return errorClassification;
	}
	public void setErrorClassification(String errorClassification) {
		this.errorClassification = errorClassification;
	}
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="ErrorCode")
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="LongMessage")
	public String getLongMessage() {
		return longMessage;
	}
	public void setLongMessage(String longMessage) {
		this.longMessage = longMessage;
	}
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="SeverityCode")
	public String getSeverityCode() {
		return severityCode;
	}
	public void setSeverityCode(String severityCode) {
		this.severityCode = severityCode;
	}
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="ShortMessage")
	public String getShortMessage() {
		return shortMessage;
	}
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}

	private String errorClassification;
	private String errorCode;
	private String longMessage;
	private String severityCode;
	private String shortMessage;
}
