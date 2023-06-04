package com.imaginnovate.empinfo.validator;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DateValidator implements ConstraintValidator<DateFormat, String> {

    private Boolean isOptional;
    private String dateformat;

    @Override
    public void initialize(DateFormat validDate) {
        this.isOptional = validDate.optional();
        this.dateformat = validDate.format();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean validDate = isValidFormat(dateformat, value);
        return isOptional ? (validDate || (value!=null && !value.isEmpty())) : validDate;
    }

    private static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (value != null){
                date = sdf.parse(value);
                if (!value.equals(sdf.format(date))) {
                    date = null;
                }
            }
        } catch (Exception ex) {
        	log.error("Exception occured in isValidFormat"+ex.getMessage());
        }
        return date != null;
    }
}