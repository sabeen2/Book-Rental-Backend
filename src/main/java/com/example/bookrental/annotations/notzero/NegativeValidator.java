package com.example.bookrental.annotations.notzero;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NegativeValidator implements ConstraintValidator<NotZeroNull,Integer> {
    @Override
    public void initialize(NotZeroNull constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value > 0;
    }
}
