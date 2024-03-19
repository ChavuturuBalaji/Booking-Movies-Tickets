package com.example.Booking.Movie.Tickets.CustomAnnotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return false;
    }
}
