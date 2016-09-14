package com.ebay.raptor.promotion.excel.validation;

/**
 * 
 * @author lyan2
 */
public class DoubleColumnConstraint extends ColumnConstraint {
	public static void main(String[] args) {
		DoubleColumnConstraint c = new DoubleColumnConstraint();
		System.out.println(c.isValid("123."));
	}
	
	private int digits = 0;

	public DoubleColumnConstraint() {
		super();
		this.message = "excel.valiation.double.message";
	}

	@Override
	public boolean isValid(Object value) {
		if (value != null) {
			try {
				Double.parseDouble(value.toString());
			} catch (NumberFormatException e) {
				return false;
			}
		}
		
		return true;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

}
