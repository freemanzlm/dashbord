package com.ebay.raptor.promotion.pojo.web.resp;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

public class ListingsUploadWebResponse extends BaseWebResponse {
	private Set<ConstraintViolation<Object>> errors = new HashSet<ConstraintViolation<Object>>();

	public Set<ConstraintViolation<Object>> getErrors() {
		return errors;
	}

	public void setErrors(Set<ConstraintViolation<Object>> errors) {
		this.errors = errors;
	}

}
