package com.example.attornatus.attornatus.validators;

import org.springframework.stereotype.Component;

@Component
public class AddressValidator {
    public void cep(String value) {
        if (value == null || !value.matches("\\d{5}-\\d{3}")) {
            throw new RuntimeException("CEP inválido");
        }
    }
}
