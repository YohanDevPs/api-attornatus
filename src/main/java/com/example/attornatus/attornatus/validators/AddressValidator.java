package com.example.attornatus.attornatus.validators;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.exeptions.ElementRepeatedException;
import com.example.attornatus.attornatus.exeptions.RequiredObjectIsNullException;
import com.example.attornatus.attornatus.models.Address;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressValidator {

    public void update(AddressDTO dto) {
        validateCep(dto.getCep());
        validateNullAddress(dto);
    }

    public void create(AddressDTO dto,List<Address> addresses) {
        validateCep(dto.getCep());
        validateNullAddress(dto);
        validateRepeatAddress(addresses, dto);
    }

    public void validateCep(String value) {
        if (value == null || !value.matches("\\d{5}-\\d{3}")) {
            throw new RuntimeException("CEP inválido");
        }
    }
    public void validateNullAddress(AddressDTO dto) {
        if(dto == null) {
            throw new RequiredObjectIsNullException("A person cannot register a null address");
        }
    }

    public void validateRepeatAddress(List<Address> addresses, AddressDTO dto) {
        var addressRepetead = addresses.stream()
                .filter(a -> a.equals(dto))
                .findFirst();

        if(addressRepetead.isPresent()) {
            throw new ElementRepeatedException("A person cannot register a repeated address");
        }
    }
}
