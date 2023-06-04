package com.imaginnovate.empinfo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 
 * @author RamaKrishna.PVV
 * @version 1.0
 *
 */

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface DateFormat {

    String message() default "Invalid Date format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean optional() default false;
    String format() default "DD/MM/YYYY";
}