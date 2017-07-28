package com.ebay.raptor.promotion.excel;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * 
 * @author lyan2
 */
public class ExcelError implements ConstraintViolation<Object> {
	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getMessageTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getRootBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<Object> getRootBeanClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getLeafBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getExecutableParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getExecutableReturnValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path getPropertyPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getInvalidValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> U unwrap(Class<U> type) {
		// TODO Auto-generated method stub
		return null;
	}

}
