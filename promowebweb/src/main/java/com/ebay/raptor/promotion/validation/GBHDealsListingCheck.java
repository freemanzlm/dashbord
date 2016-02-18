package com.ebay.raptor.promotion.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({ TYPE, ANNOTATION_TYPE })  
@Retention(RUNTIME)  
@Constraint(validatedBy = GBHDealsListingValidator.class)  
@Documented 
public @interface GBHDealsListingCheck {
	String message() default "{GBHDealsListingCheck.defult}";  

    Class<?>[] groups() default { };  

    Class<? extends Payload>[] payload() default { };  

    @Target({TYPE, ANNOTATION_TYPE })  
    @Retention(RUNTIME)  
    @Documented  
    @interface List {  
    	GBHDealsListingCheck[] value();  
    }  
}
