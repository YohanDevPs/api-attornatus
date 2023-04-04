package com.example.attornatus.attornatus.services;

import com.example.attornatus.attornatus.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    List<AddressDTO> findAllByPersonId(Long id);
    AddressDTO findById(Long id);
    AddressDTO create(Long idPerson, AddressDTO dto);
    AddressDTO update(AddressDTO dto);
    void delete(Long id);
}
