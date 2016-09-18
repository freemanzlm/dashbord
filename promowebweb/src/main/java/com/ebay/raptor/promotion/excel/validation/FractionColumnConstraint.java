package com.ebay.raptor.promotion.excel.validation;

/**
 * 
 * @author lyan2
 */
public class FractionColumnConstraint extends ColumnConstraint {
	
	private int precision;
	
	public FractionColumnConstraint() {
		super();
		this.message = "excel.valiation.fraction.message";
	}

	/**
	 * Return false only if value is NaN.
	 */
	public boolean isValid(Object value) {
		
		if (value != null) {
			Double dValue = null;
			try {
				dValue = Double.valueOf(value.toString());
			} catch (Exception e) {
				return false;
			}
			
			if (dValue != null) {
				String str = value.toString();
				int dotIndex = str.indexOf(".") + 1;
				if (dotIndex + precision < str.length()) {
					return false;
				}
			}
		}
		
		return true;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}
	
//	public static void main(String[] args) {
//		FractionColumnConstraint constraint = new FractionColumnConstraint();
//		constraint.setPrecision(2);
//		System.out.println(constraint.isValid(34.8));
//		System.out.println(constraint.isValid(34.883));
//		System.out.println(constraint.isValid(34d));
//		System.out.println(constraint.isValid(34.));
//	}
	
}
