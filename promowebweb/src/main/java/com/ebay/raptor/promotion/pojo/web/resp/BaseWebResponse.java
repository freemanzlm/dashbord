package com.ebay.raptor.promotion.pojo.web.resp;


/**
 * 
 * @author lyan2
 */
public class BaseWebResponse {

	private boolean status = Boolean.TRUE;
	private String message;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
