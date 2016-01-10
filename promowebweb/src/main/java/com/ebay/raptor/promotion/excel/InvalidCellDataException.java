package com.ebay.raptor.promotion.excel;

import org.springframework.validation.Errors;

import com.ebay.app.raptor.promocommon.CommonException;

public class InvalidCellDataException extends CommonException {

	private static final long serialVersionUID = 1637243229951110986L;
	
	public InvalidCellDataException (Errors errors, int rowIndex) {
		super(errors.getAllErrors().toString());
	}
	
	public InvalidCellDataException (Errors errors, int rowIndex, Throwable cause) {
		super(errors.getAllErrors().toString(), cause);
	}
	
	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	private Errors errors;
	private int rowIndex;
}
