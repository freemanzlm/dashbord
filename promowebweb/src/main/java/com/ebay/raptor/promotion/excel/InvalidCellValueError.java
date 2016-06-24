package com.ebay.raptor.promotion.excel;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

public class InvalidCellValueError implements ConstraintViolation<Object> {

	public InvalidCellValueError(int rowIndex, int colIndex, Object value, String message) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		this.cellValue = value;
		this.message = message;
	}	
	
	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Object getCellValue() {
		return cellValue;
	}

	public void setCellValue(Object cellValue) {
		this.cellValue = cellValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private int colIndex;
	private int rowIndex;
	private Object cellValue;
	private String message;

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
	public Class getRootBeanClass() {
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
	public ConstraintDescriptor getConstraintDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object unwrap(Class type) {
		// TODO Auto-generated method stub
		return null;
	}
}
