package com.course.registration.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameValidator.class)
@Documented
public @interface ValidName {

        String message() default "coursename provided should not be empty";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

