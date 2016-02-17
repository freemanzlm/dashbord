package com.ebay.raptor.promotion.excel;

import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.raptor.promotion.excep.PromoException;

public class UnsupportExcelDataTypeException extends PromoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5440301782804185491L;
	
	public UnsupportExcelDataTypeException (String dataType) {
		super(ErrorType.UnsupportCellType, dataType);
	}

	public UnsupportExcelDataTypeException (Throwable cause, String dataType) {
		super(ErrorType.UnsupportCellType, cause, dataType);
	}
}
