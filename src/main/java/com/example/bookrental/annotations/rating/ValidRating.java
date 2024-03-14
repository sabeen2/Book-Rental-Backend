package com.example.bookrental.annotations.rating;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RatingValidator.class)
public @interface ValidRating {
    String message() default "Rating must be out of 10";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
