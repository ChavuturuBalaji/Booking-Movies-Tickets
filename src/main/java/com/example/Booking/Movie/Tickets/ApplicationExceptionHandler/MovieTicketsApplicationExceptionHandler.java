package com.example.Booking.Movie.Tickets.ApplicationExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class MovieTicketsApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String ,String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        Map<String ,String > errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(i -> errors.put(i.getField(),i.getDefaultMessage()));
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String,String> constraintViolationException(ConstraintViolationException exception){
        Map<String ,String> errors = new HashMap<>();
        exception.getConstraintViolations().forEach(i -> errors.put(i.getPropertyPath().toString(),i.getMessage()));
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public List<String > dataIntegrityViolationExceptionHandler(DataIntegrityViolationException exception){
        List<String> errors = new ArrayList<>();
        errors.add("Mobile is already used please enter new mobile number");
        return errors;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Parameter '" + ex.getName() + "' must be of type '" + ex.getRequiredType().getSimpleName() + "'.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


}
