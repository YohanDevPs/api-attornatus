package com.example.attornatus.attornatus.validators;

import javax.validation.ConstraintValidatorContext;

public interface ConstraintValidator<T, T1> {
    void isValid(String value, ConstraintValidatorContext context);
}
