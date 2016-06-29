package com.ebay.raptor.promotion.excel.validation;

import java.util.Iterator;
import java.util.Set;

/**
 * Value must be a value of a set.
 * @author lyan2
 */
public class RangeColumnConstraint extends ColumnConstraint {
	private String[]  pickList;
	private Boolean mustInRange = true;

	public RangeColumnConstraint() {
		this.message = "excel.validation.range.message";
	}
	
	@Override
	public boolean isValid(Object value) {
		for (String entry : pickList) {
			if (equal(entry, value)) {
				return true;
			}
		}
		/*if (pickList != null && !pickList.isEmpty()) {
			Iterator<Object> iter = pickList.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (equal(obj, value)) {
					return true;
				}
			}
		}*/
		
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
