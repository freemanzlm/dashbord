package com.ebay.raptor.promotion.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ebay.raptor.promotion.pojo.business.GBHDealsListing;
import com.ebay.raptor.promotion.pojo.business.ShipOption;

public class GBHDealsListingValidator implements ConstraintValidator<GBHDealsListingCheck, GBHDealsListing> {

	@Override
	public void initialize(GBHDealsListingCheck constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(GBHDealsListing gbhListing, ConstraintValidatorContext context) {
		if (gbhListing == null) {
			return true;
		}
		
		if (gbhListing.getWwShipOpt() == ShipOption.Yes
				&& (gbhListing.getWwShipChg() == null
					|| gbhListing.getWwShipChg() < 0)) {
			context.disableDefaultConstraintViolation();  
            context.buildConstraintViolationWithTemplate("{listing.wwShipChg.invalid}")  
                    .addPropertyNode("wwShipChg")  
                    .addConstraintViolation();  
            return false; 
		} else if (gbhListing.getRuShipOpt() == ShipOption.Yes
				&& (gbhListing.getRuShipChg() == null
					|| gbhListing.getRuShipChg() < 0)) {
			context.disableDefaultConstraintViolation();  
	        context.buildConstraintViolationWithTemplate("{listing.ruShipChg.invalid}")  
	                .addPropertyNode("ruShipChg")  
	                .addConstraintViolation();  
	        return false; 
		} else if (gbhListing.getCnShipOpt() == ShipOption.Yes
				&& (gbhListing.getCnShipChg() == null
					|| gbhListing.getCnShipChg() < 0)) {
			context.disableDefaultConstraintViolation();  
	        context.buildConstraintViolationWithTemplate("{listing.cnShipChg.invalid}")  
	                .addPropertyNode("cnShipChg")  
	                .addConstraintViolation();  
	        return false; 
		} else if (gbhListing.getLaShipOpt() == ShipOption.Yes
				&& (gbhListing.getLaShipChg() == null
					|| gbhListing.getLaShipChg() < 0)) {
			context.disableDefaultConstraintViolation();  
	        context.buildConstraintViolationWithTemplate("{listing.laShipChg.invalid}")  
	                .addPropertyNode("laShipChg")  
	                .addConstraintViolation();  
	        return false; 
		} else if (gbhListing.getMeShipOpt() == ShipOption.Yes
				&& (gbhListing.getMeShipChg() == null
					|| gbhListing.getMeShipChg() < 0)) {
			context.disableDefaultConstraintViolation();  
		    context.buildConstraintViolationWithTemplate("{listing.meShipChg.invalid}")  
		            .addPropertyNode("meShipChg")  
		            .addConstraintViolation();  
		    return false; 
		} else if (gbhListing.getBrShipOpt() == ShipOption.Yes
				&& (gbhListing.getBrShipChg() == null
					|| gbhListing.getBrShipChg() < 0)) {
			context.disableDefaultConstraintViolation();  
		    context.buildConstraintViolationWithTemplate("{listing.brShipChg.invalid}")  
		            .addPropertyNode("brShipChg")  
		            .addConstraintViolation();  
		    return false; 
		} else if (gbhListing.getIsShipOpt() == ShipOption.Yes
				&& (gbhListing.getIsShipChg() == null
					|| gbhListing.getIsShipChg() < 0)) {
			context.disableDefaultConstraintViolation();  
		    context.buildConstraintViolationWithTemplate("{listing.isShipChg.invalid}")  
		            .addPropertyNode("isShipChg")  
		            .addConstraintViolation();  
		    return false; 
		}
		
		if (gbhListing.getWwShipOpt() == ShipOption.No
				&& gbhListing.getRuShipOpt() == ShipOption.No
				&& gbhListing.getCnShipOpt() == ShipOption.No
				&& gbhListing.getLaShipOpt() == ShipOption.No
				&& gbhListing.getMeShipOpt() == ShipOption.No
				&& gbhListing.getBrShipOpt() == ShipOption.No
				&& gbhListing.getIsShipOpt() == ShipOption.No) {
			context.disableDefaultConstraintViolation();  
		    context.buildConstraintViolationWithTemplate("{listing.shipOpt.allNo}") 
		            .addConstraintViolation();  
		    return false; 
		}
		
		return true;
	}

}
