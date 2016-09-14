package com.ebay.raptor.promotion.util;

/**
 * 
 * @author xiatu
 */
public enum AttachmentAllowedFileType {
	
	/**
	 * PDF
	 */
	pdf("25504446"),
	
	/**
	 * DOC
	 */
	doc("D0CF11E0"),
	
	
	/**
	 * DOCX
	 */
	docx("504B0304"),
	
	/**
	 * XLS
	 */
	xls("504B0304"),
	
	/**
	 * XLSX
	 */
	xlsx("504B0304"),
	
	/**
	 * JPG
	 */
	jpg("FFD8FF"),
	
	/**
	 * JPG Still Picture Interchange File Format
	 */
	/*JPG2("FFD8FFE8"),*/
	
	/**
	 * GIF
	 */
	gif("47494638"),
	
	/**
	 * JPEG
	 */
	zip("504B0304"),
	
	/**
	 * RAR
	 */
	rar("52617221");
	
	private String value = "";
	
	private AttachmentAllowedFileType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
