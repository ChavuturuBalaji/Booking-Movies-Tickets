package com.example.Booking.Movie.Tickets.CustomAnnotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile,String > {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        for (char ch: s.toCharArray()){
            if(!Character.isDigit(ch)){
                return false;
            }
        }
        if(s.length()!= 10){
            return false;
        } else if (s.startsWith("0") || s.startsWith("1") || s.startsWith("2")
                || s.startsWith("3") || s.startsWith("4") || s.startsWith("5")) {
            return false;
        }
        return true;
    }
}