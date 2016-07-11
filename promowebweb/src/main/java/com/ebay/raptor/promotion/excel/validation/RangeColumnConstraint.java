package com.ebay.raptor.promotion.excel.validation;


/**
 * Value must be a value of a set.
 * @author lyan2
 */
public class RangeColumnConstraint extends ColumnConstraint {
	private String[]  pickList;
	private Boolean mustInRange = true;

	public RangeColumnConstraint() {
		super();
		this.message = "excel.validation.range.message";
	}
	
	@Override
	public boolean isValid(Object value) {
		if (!this.getMustInRange()) {
			return true; 
		}
		
		for (String entry : pickList) {
			if (equal(entry, value)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String[] getPickList() {
		return pickList;
	}

	public void setPickList(String[] pickList) {
		this.pickList = pickList;
	}

	public Boolean getMustInRange() {
		return mustInRange;
	}

	public void setMustInRange(Boolean mustInRange) {
		this.mustInRange = mustInRange;
	}
	
}
