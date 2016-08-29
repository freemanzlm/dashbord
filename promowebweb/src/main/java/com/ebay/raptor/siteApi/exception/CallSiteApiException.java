package com.ebay.raptor.siteApi.exception;

/**
 * 
 * @author lyan2
 */
public class CallSiteApiException extends Exception {

	private static final long serialVersionUID = -6832746667719398299L;

	public CallSiteApiException(String msg) {
		super(msg);
	}
	
	public CallSiteApiException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
