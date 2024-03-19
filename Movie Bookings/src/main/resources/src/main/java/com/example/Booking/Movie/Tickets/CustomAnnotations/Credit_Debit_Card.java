package com.example.Booking.Movie.Tickets.CustomAnnotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CardValidator.class)
public @interface Credit_Debit_Card {
    public String message() default "Invalid Credit card number must contain 16 digits and doesn't start with 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
