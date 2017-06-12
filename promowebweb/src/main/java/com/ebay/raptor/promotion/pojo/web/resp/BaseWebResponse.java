package com.ebay.raptor.promotion.pojo.web.resp;

import java.util.List;


/**
 * 
 * @author lyan2
 */
public class BaseWebResponse {

	private boolean status = Boolean.TRUE;
	private String message;
	@SuppressWarnings("rawtypes")
	private List errors;

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

	@SuppressWarnings("rawtypes")
	public List getErrors() {
		return errors;
	}

	@SuppressWarnings("rawtypes")
	public void setErrors(List errors) {
		this.errors = errors;
	}

}
