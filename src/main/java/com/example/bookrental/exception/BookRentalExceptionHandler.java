package com.example.bookrental.exception;

import com.example.bookrental.generic_response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class BookRentalExceptionHandler {

    private final CustomMessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericResponse<Map<String, String>> invalidArgumentHandler(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> map.put(error.getField(), error.getDefaultMessage()));
        return GenericResponse.<Map<String, String>>builder()
                .success(false)
                .message(messageSource.get(ExceptionMessages.METHOD_INVALID.getCode()))
                .data(map)
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public GenericResponse<Map<String, String>> userNotFoundException(NotFoundException e) {
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", e.message);
        return GenericResponse.<Map<String, String>>builder()
                .success(false)
                .message(messageSource.get(ExceptionMessages.NOT_FOUND_EXCEPTION.getCode()))
                .data(map)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public GenericResponse<Map<String, String>> globalException(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", e.getMessage());
        return GenericResponse.<Map<String, String>>builder()
                .success(false)
                .message(messageSource.get(ExceptionMessages.EXCEPTION.getCode()))
                .data(map)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public GenericResponse<Map<String, String>> handleConstraintViolation(DataIntegrityViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            org.hibernate.exception.ConstraintViolationException violation = ((org.hibernate.exception.ConstraintViolationException) ex.getCause());

            //handling constraint violations
            if (violation.getMessage().contains("unique")) {
                errors.put("errorMessage", "The data violates a unique constraint.");
            }
        } else if (ex.getCause() instanceof org.hibernate.exception.DataException) {
            org.hibernate.exception.DataException violation = ((org.hibernate.exception.DataException) ex.getCause());

            // handling data exceptions
            if (violation.getCause().toString().contains("value too long for type character varying")) {
                errors.put("errorMessage", "The length of the provided data exceeds the limit.");
            }
        } else if (ex.getCause() instanceof GenericJDBCException) {
            GenericJDBCException e = (GenericJDBCException) ex.getCause();
            String message = e.getMessage();
            //handling generic JDBC exceptions
            if (message.contains("function_exception_")) {
                errors.put("errorMessage", "An error occurred with a database function.");
            }
        } else {
            String errorMessage = ex.getMessage();
            errors.put("errorMessage", errorMessage);
        }

        //handling for unhandled exceptions

        return GenericResponse.<Map<String, String>>builder()
                .success(false)
                .message(messageSource.get(ExceptionMessages.EXCEPTION.getCode()))
                .data(errors)
                .build();
    }
}
