package com.example.attornatus.attornatus.services;

import com.example.attornatus.attornatus.dto.PersonDTO;
import com.example.attornatus.attornatus.models.Address;

import java.util.List;

public interface PersonService {

    List<PersonDTO> findAll();
    PersonDTO findById(Long id);
    PersonDTO create(PersonDTO dto);
    PersonDTO update(PersonDTO dto);
    void delete(Long id);
    List<Address> getAddressesByPersonId(Long id);
}
