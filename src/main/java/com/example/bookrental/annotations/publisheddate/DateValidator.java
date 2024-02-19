package com.example.bookrental.annotations.publisheddate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Date;

public class DateValidator implements ConstraintValidator <PublishedDateValidator, Date>{
    @Override
    public void initialize(PublishedDateValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        return value == null || value.before(new Date());
    }
}
