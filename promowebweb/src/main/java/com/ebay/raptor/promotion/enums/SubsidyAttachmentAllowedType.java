package com.ebay.raptor.promotion.enums;

public enum SubsidyAttachmentAllowedType {
	/**
	 * PDF
	 */
	pdf("25504446"),
	
	/**
	 * JPEG
	 */
	zip("504B0304"),
	/**
	 * JPEG
	 */
	jpeg("FFD8FF"),
	
	/**
	 * JPG
	 */
	jpg("FFD8FF");
	
	private String value = "";
	
	private SubsidyAttachmentAllowedType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
