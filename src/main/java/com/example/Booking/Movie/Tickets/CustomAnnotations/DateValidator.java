package com.example.Booking.Movie.Tickets.CustomAnnotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class DateValidator implements ConstraintValidator<Date,String > {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String[] date = value.split("/");
        if (date.length >3){
            return false;
        }
        if (date[0].length()>2){
            return false;
        }else if (date[1].length()>2){
            return false;
        } else
            return date[2].length() <= 4;
    }
}
