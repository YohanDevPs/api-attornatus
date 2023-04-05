package com.example.attornatus.attornatus.services.autoupdate;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.models.Address;
import com.example.attornatus.attornatus.repositorys.AddressRepository;
import com.example.attornatus.attornatus.services.AddressService;
import com.example.attornatus.attornatus.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoUpdateServiceImpl implements AutoUpdateService {

    @Autowired
    private AddressRepository repository;
    @Autowired
    private PersonService personService;

    @Override
    public void setOldMainAddressToFalse(Long idPerson, AddressDTO dto) {
        if(dto.isMainAddress() == true) {
            var oldMainAddress = personService.getAddressesByPersonId(idPerson)
                    .stream()
                    .filter(Address::isMainAddress)
                    .findFirst()
                    .orElse(null);

            if(oldMainAddress != null) {
                oldMainAddress.setMainAddress(false);
                repository.save(oldMainAddress);
            }
        }
    }
}
