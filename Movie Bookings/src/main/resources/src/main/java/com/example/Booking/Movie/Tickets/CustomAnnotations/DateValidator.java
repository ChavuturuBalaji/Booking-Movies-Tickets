package com.example.Booking.Movie.Tickets.CustomAnnotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class DateValidator implements ConstraintValidator<Date,String > {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String[] date = value.split("/");
        if (date.length != 3)
            return false;

        if (date[0].length()>2){
            return false;
        }else if (date[1].length()>2){
            return false;
        } else if(date[2].length()>4)
            return false;

        try {
            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);

            if (year < 2020 || year > 2025)
                return false;
            if (month < 1 || month > 12)
                return false;
            return day >= 1 && day <= daysInMonth(month, year);
        }
        catch (NumberFormatException e){
            return false;
        }

    }

    private int daysInMonth(int month, int year) {
        if (month == 2) {
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
                return 29;
            else
                return 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            return 31;
        }
    }

}
