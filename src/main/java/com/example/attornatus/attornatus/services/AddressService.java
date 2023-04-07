package com.example.attornatus.attornatus.services;

import com.example.attornatus.attornatus.dto.AddressDTO;

public interface AddressService {

    AddressDTO findById(Long id);
    AddressDTO create(Long idPerson, AddressDTO dto) throws Exception;
    AddressDTO update(AddressDTO dto);
    void delete(Long id);
}
