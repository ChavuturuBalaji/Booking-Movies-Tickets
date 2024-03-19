package com.example.Booking.Movie.Tickets.CustomAnnotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardValidator implements ConstraintValidator<Credit_Debit_Card,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        for (char ch: s.toCharArray()){
            if (!Character.isDigit(ch)){
                return false;
            }

        }

        if (s.startsWith("0")){
            return false;
        }
        else if (s.length() == 16){
            return true;
        }
        return false;
    }
}
