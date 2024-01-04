package com.example.bookrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BookRentalExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> invalidArgumentHandler(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            map.put(error.getField(), error.getDefaultMessage());
        });
        return map;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> userNotFoundException(NotFoundException e) {
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", e.message);
        return map;
    }


}
