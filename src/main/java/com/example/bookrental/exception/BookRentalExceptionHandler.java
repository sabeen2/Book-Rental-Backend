package com.example.bookrental.exception;

import com.example.bookrental.generic_response.GenericResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
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
//    @ExceptionHandler(Exception.class)
//    public GenericResponse<String> handleSecurity(Exception ex) {
//        ProblemDetail problemDetail=null;
//
//        if (ex instanceof BadCredentialsException) {
//            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
//            problemDetail.setProperty(("access_denied"), "Bad credentials");
//        }
//
//        if (ex instanceof AccessDeniedException) {
//            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//            problemDetail.setProperty(("access_denied"), "Not authorized");
//        }
//        if(ex instanceof SignatureException){
//            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//            problemDetail.setProperty(("access_denied"), "Not valid Jwt");
//        }
//        if(ex instanceof ExpiredJwtException){
//            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//            problemDetail.setProperty(("access_denied"), "Jwt token Expired");
//        }
//
//        return GenericResponse.<String>builder()
//                .success(false)
//                .message(ex.getMessage())
//                .data(problemDetail.getProperties().values().toString())
//                .build();
//    }

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public GenericResponse<Map<String, String>> globalException(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", e.getMessage());
        return GenericResponse.<Map<String, String>>builder()
                .success(false)
                .message("Exception Thrown")
                .data(map)
                .build();
    }
}
