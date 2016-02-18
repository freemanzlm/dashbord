package com.ebay.raptor.promotion.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LinkValidator implements ConstraintValidator<Link, String> {

	@Override
	public void initialize(Link constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ( value == null || value.length() == 0 ) {
			return false;
		}

		return true;
	}

}
