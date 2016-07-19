package com.ebay.raptor.promotion.util;

/**
 * 
 * @author xiatu
 */
public enum AttachmentAllowedFileType {
	
	/**
	 * PDF
	 */
	PDF("25504446"),
	
	/**
	 * DOC
	 */
	DOC("0D444F43"),
	
	/**
	 * DOCX
	 */
	DOCX("504B0304"),
	
	/**
	 * XLS
	 */
	XLS("09081000"),
	
	/**
	 * XLSX
	 */
	XLSX("504B0304"),
	
	/**
	 * JPG
	 */
	JPG("FFD8FFE0"),
	
	/**
	 * JPG Digital camera JPG
	 */
	JPG2("FFD8FFE1"),
	
	/**
	 * JPG Still Picture Interchange File Format
	 */
	JPG3("FFD8FFE8"),
	
	/**
	 * GIF
	 */
	GIF("47494638"),
	
	/**
	 * JPEG
	 */
	ZIP("504B0304"),
	
	/**
	 * RAR
	 */
	RAR("52617221");
	
	private String value = "";
	
	private AttachmentAllowedFileType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
