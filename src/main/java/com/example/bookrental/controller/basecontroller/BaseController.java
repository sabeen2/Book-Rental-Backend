package com.example.bookrental.controller.basecontroller;

import com.example.bookrental.generic_response.GenericResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.CrossOrigin;

@SecurityRequirement(name = "bookRental")
@CrossOrigin(origins = { "http://localhost:5173","https://book-rental-system-ts.netlify.app/",},allowedHeaders = "*")
public class BaseController {
    protected <T>GenericResponse<T> successResponse(T data, String message) {
        return GenericResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }
    protected <T>GenericResponse<T> errorResponse(String errorMessage) {
        return GenericResponse.<T>builder()
                .success(false)
                .message(errorMessage)
                .build();
    }
}
