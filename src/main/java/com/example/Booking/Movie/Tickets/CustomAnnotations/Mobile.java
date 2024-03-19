package com.example.Booking.Movie.Tickets.CustomAnnotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface Mobile {
    public String message() default "Invalid Mobile Number must be 10 digits doesn't start with 0,1,2,3,4,5";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
