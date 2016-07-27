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
	doc("0D444F43"),
	
	/**
	 * DOCX
	 */
	docx("504B0304"),
	
	/**
	 * XLS
	 */
	xls("09081000"),
	
	/**
	 * XLSX
	 */
	xlsx("504B0304"),
	
	/**
	 * JPG
	 */
	jpg("FFD8FFE0"),
	
	/**
	 * JPG Digital camera JPG
	 */
	JPG("FFD8FFE1"),
	
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
