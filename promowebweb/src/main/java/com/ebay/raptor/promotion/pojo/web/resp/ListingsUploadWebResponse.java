package com.ebay.raptor.promotion.pojo.web.resp;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class ListingsUploadWebResponse extends BaseWebResponse {
	private Set<ConstraintViolation<Object>> errors;

	public Set<ConstraintViolation<Object>> getErrors() {
		return errors;
	}

	public void setErrors(Set<ConstraintViolation<Object>> errors) {
		this.errors = errors;
	}

}
