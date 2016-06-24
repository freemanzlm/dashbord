package com.ebay.raptor.promotion.excel.validation;

/**
 * Validate if text is too long.
 * 
 * @author lyan2
 */
public class LengthColumnConstraint extends ColumnConstraint {

	private int length;
	
	public LengthColumnConstraint(int maxlength) {
		this.length = maxlength;
		this.message = "excel.valiation.length.message";
	}

	public boolean isValid(Object value) {
		String text = null;
		
		if (null != value && !(value instanceof String)) {
			text = value.toString();
		} else {
			text = (String) value;
		}

		if (text != null && text.length() > length) {
			return false;
		}

		return true;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return length;
	}

}
