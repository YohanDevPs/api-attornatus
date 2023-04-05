package com.example.attornatus.attornatus.services;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.models.Address;

import java.util.List;

public interface AddressService {

    List<AddressDTO> findAllByPersonId(Long id);
    List<Address> getAddressesByPersonId(Long id);
    AddressDTO findById(Long id);
    AddressDTO create(Long idPerson, AddressDTO dto) throws Exception;
    AddressDTO update(AddressDTO dto);
    void delete(Long id);
}
