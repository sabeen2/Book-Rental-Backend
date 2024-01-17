package com.example.bookrental.exception;

import com.example.bookrental.generic_response.GenericResponse;
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
    public GenericResponse<Map<String, String>> invalidArgumentHandler(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> map.put(error.getField(), error.getDefaultMessage()));
        return GenericResponse.<Map<String, String>>builder()
                .success(false)
                .message("Method Argument Not Valid Exception is Thrown")
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
                .message("Not found Exception Thrown")
                .data(map)
                .build();
    }
}
