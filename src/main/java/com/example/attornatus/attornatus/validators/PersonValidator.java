package com.example.attornatus.attornatus.validators;

import com.example.attornatus.attornatus.dto.PersonDTO;
import com.example.attornatus.attornatus.exeptions.RequiredObjectIsNullException;
import org.springframework.stereotype.Component;

@Component
public class PersonValidator {

    public void create(PersonDTO dto) {
        validateNullPersonDTO(dto);
    }

    public void update(PersonDTO dto) {
        validateNullPersonDTO(dto);
    }

    public void validateNullPersonDTO(PersonDTO dto) {
        if(dto == null) {
            throw new RequiredObjectIsNullException("Not allowed register a null person");
        }
    }
}
