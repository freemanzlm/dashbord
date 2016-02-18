package com.ebay.raptor.promotion.excel;

import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.raptor.promotion.excep.PromoException;

public class UnsupportFieldDataTypeException extends PromoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5440301782804185491L;
	
	public UnsupportFieldDataTypeException (String dataType) {
		super(ErrorType.UnsupportFieldType, dataType);
	}

	public UnsupportFieldDataTypeException (Throwable cause, String dataType) {
		super(ErrorType.UnsupportFieldType, cause, dataType);
	}
}
