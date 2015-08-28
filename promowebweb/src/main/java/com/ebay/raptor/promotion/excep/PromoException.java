package com.ebay.raptor.promotion.excep;

public class PromoException extends Exception {

	private static final long serialVersionUID = 8614631264999658826L;

	public PromoException() {
		super();
	}

	public PromoException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PromoException(String message, Throwable cause) {
		super(message, cause);
	}

	public PromoException(String message) {
		super(message);
	}

	public PromoException(Throwable cause) {
		super(cause);
	}

}
