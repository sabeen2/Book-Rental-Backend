package com.example.bookrental.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessages {
    SUCCESS("response.success"),
    FAIL("response.failed");
    final String code;
}
