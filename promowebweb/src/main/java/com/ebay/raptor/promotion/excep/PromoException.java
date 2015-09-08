package com.ebay.raptor.promotion.excep;

import com.ebay.app.raptor.promocommon.CommonException;
import com.ebay.app.raptor.promocommon.error.ErrorType;

public class PromoException extends CommonException {

	private static final long serialVersionUID = 8614631264999658826L;
	
	public PromoException (ErrorType errorType, Object ...args) {
		super(String.format("Error Code " + errorType.getCode() + " - " + errorType.getErrorMsg(), args));
		this.setErrorType(errorType);
		this.setArgs(args);
	}
	
	public PromoException (ErrorType errorType, Throwable cause, Object ...args) {
		super(String.format("Error Code " + errorType.getCode() + " - " + errorType.getErrorMsg(), args), cause);
		this.setErrorType(errorType);
		this.setArgs(args);
	}

	public PromoException() {
		super();
	}

	public PromoException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setErrorType(ErrorType.Default);
	}

	public PromoException(String message, Throwable cause) {
		super(message, cause);
		this.setErrorType(ErrorType.Default);
	}

	public PromoException(String message) {
		super(message);
		this.setErrorType(ErrorType.Default);
	}

	public PromoException(Throwable cause) {
		super(cause);
		this.setErrorType(ErrorType.Default);
	}

}
