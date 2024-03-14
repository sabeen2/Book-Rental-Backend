package com.example.bookrental.annotations.rating;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<ValidRating, Double> {
    @Override
    public void initialize(ValidRating constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double rating, ConstraintValidatorContext context) {
        return rating != null && rating >= 0 && rating <= 10;
    }
}
