package com.example.bookrental.annotations.publisheddate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PublishedDateValidator {
    String message() default "Date must not be in future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
