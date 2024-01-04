package com.example.bookrental.generic_response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GenericResponse<T> {
    private boolean success;
    private String message;
    //T is a type parameter or type variable
    private T data;
}

