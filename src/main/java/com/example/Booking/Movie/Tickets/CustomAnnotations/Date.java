package com.example.Booking.Movie.Tickets.CustomAnnotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Date {
    public String message() default "day and month contain 2 digits year contain 4 digits (dd/MM/yyyy)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
