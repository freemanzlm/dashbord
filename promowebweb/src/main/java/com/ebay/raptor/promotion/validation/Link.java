package com.ebay.raptor.promotion.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.Length;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)  
@Constraint(validatedBy = LinkValidator.class)  
@Documented
@Length(max=2000)
public @interface Link {
	String message() default "{link.defult}";  

    Class<?>[] groups() default { };  

    Class<? extends Payload>[] payload() default { };  

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER }) 
    @Retention(RUNTIME)  
    @Documented  
    @interface List {  
    	Link[] value();  
    }
}
