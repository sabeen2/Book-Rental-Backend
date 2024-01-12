package com.example.bookrental.controller.basecontroller;

import com.example.bookrental.generic_response.GenericResponse;

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
