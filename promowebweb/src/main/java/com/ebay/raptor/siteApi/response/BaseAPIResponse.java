package com.ebay.raptor.siteApi.response;

import javax.xml.bind.annotation.XmlElement;

import com.ebay.raptor.siteApi.ErrorInfo;

/**
 * 
 * @author lyan2
 */
public class BaseAPIResponse {
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="Ack")
	public String getAck() {
		return ack;
	}
	public void setAck(String ack) {
		this.ack = ack;
	}
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="Build")
	public String getBuild() {
		return build;
	}
	public void setBuild(String build) {
		this.build = build;
	}
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="CorrelationId")
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="Errors")
	public ErrorInfo getErrors() {
		return errors;
	}
	public void setErrors(ErrorInfo errors) {
		this.errors = errors;
	}
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="Timestamp")
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="Version")
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	private String ack;
	private String build;
	private String correlationId;
	private ErrorInfo errors;
	private String timestamp;
	private String version;
}
