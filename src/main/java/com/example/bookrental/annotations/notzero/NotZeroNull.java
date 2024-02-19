package com.example.bookrental.annotations.notzero;


import com.example.bookrental.annotations.publisheddate.DateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NegativeValidator.class)  //class that contains validation logic
@Target(ElementType.FIELD) //specifies where the PublishedDateValidator ie in this case in field
@Retention(RetentionPolicy.RUNTIME)  //specifies the retention policy ie-:when to be used
public @interface NotZeroNull {
    String message() default "Cannot be negative or zero";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
