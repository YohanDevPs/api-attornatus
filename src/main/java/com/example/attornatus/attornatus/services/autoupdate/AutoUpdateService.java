package com.example.attornatus.attornatus.services.autoupdate;

import com.example.attornatus.attornatus.dto.AddressDTO;

public interface AutoUpdateService {

    void setOldMainAddressToFalse(Long idPerson, AddressDTO dto);
}
