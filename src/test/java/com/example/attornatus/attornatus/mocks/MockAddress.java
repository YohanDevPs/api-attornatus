package com.example.attornatus.attornatus.mocks;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.models.Address;

import java.util.ArrayList;
import java.util.List;

public class MockAddress {
    
    public Address mockEntity() {
        return mockEntity(0);
    }

    public AddressDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Address> mockEntityList() {
        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            addresses.add(mockEntity(i));
        }
        return addresses;
    }

    public List<AddressDTO> mockDTOList() {
        List<AddressDTO> addressesDTO = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            addressesDTO.add(mockDTO(i));
        }
        return addressesDTO;
    }

    public Address mockEntity(Integer number) {
        Address Address = new Address();
        Address.setId(number.longValue());
        Address.setCep("12345-23" + number);
        Address.setCity("Salvador" + number);
        Address.setStreet("Rua Manoel Gomes de Mendonca" + number);
        Address.setNumber(number);
        return Address;
    }

    public AddressDTO mockDTO(Integer number) {
        AddressDTO Address = new AddressDTO();
        Address.setId(number.longValue());
        Address.setCep("12345-23" + number);
        Address.setCity("Salvador" + number);
        Address.setStreet("Rua Manoel Gomes de Mendonca" + number);
        Address.setNumber(number);
        return Address;
    }
}
