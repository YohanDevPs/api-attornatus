package com.example.attornatus.attornatus.validators;

import com.example.attornatus.attornatus.annotations.Cep;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CepValidator implements ConstraintValidator<Cep, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || !value.matches("\\d{5}-\\d{3}")) {
            return false;
        }
        return true;
    }
}
