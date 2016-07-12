package com.ebay.raptor.promotion.enums;

/**
 * 
 * @author lyan2
 */
public enum ListingState implements ICustomEnum {
	Confirmed("Confirmed", 0),      // PMListingVersion.VERSION_CONFIRMED, NOT PMListingStatus.Uploaded.
	Uploaded("Uploaded", 1), 		// PMListingStatus.Uploaded
	Submitted("Submitted", 2),		// PMListingStatus.PRETRIAL
	Applied("Applied", 3),		// PMListingStatus.APPLIED, PMListingStatus.PRETRIAL_PASS, PMListingStatus.PRITRIAL_FAILED
	Approved("Approved", 4)		// PMListingStatus.AUDIT_SUCCESS, PMListingStatus.AUDIT_FAILED, PMListingStatus.PRETRIAL_PASS, PMListingStatus.PRITRIAL_FAILED
;
	
	private final String name;
	private final int code;

	private ListingState(String name, int code) {
		this.name = name;
		this.code = code;
	}

	@Override
	public String value() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}
}
