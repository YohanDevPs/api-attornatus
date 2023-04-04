package com.example.attornatus.attornatus.annotations;

import com.example.attornatus.attornatus.validators.CepValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CepValidator.class})
public @interface Cep {
    String message() default "Invalid CEP";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}