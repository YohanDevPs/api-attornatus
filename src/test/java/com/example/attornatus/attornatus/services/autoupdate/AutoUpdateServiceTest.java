package com.example.attornatus.attornatus.services.autoupdate;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.mocks.MockAddress;
import com.example.attornatus.attornatus.models.Address;
import com.example.attornatus.attornatus.repositorys.AddressRepository;
import com.example.attornatus.attornatus.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class AutoUpdateServiceTest {


    MockAddress inputAddress;
    @InjectMocks
    private AutoUpdateServiceImpl autoUpdateService;
    @Mock
    private AddressRepository repository;
    @Mock
    private PersonService personService;

    @BeforeEach
    void setUpMocks() {
        inputAddress = new MockAddress();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetOldMainAddressToFalse() {
        Long personId = 1L;
        AddressDTO dto = new AddressDTO();
        dto.setMainAddress(true);

        Address oldMainAddress = mock(Address.class);
        when(oldMainAddress.isMainAddress()).thenReturn(true);

        List<Address> addresses = new ArrayList<>();
        addresses.add(oldMainAddress);

        when(personService.findAddressesEntitiesByPersonId(personId)).thenReturn(addresses);
        when(repository.save(oldMainAddress)).thenReturn(oldMainAddress);

        autoUpdateService.setOldMainAddressToFalse(personId, dto);

        verify(repository, times(1)).save(oldMainAddress);
        verify(oldMainAddress, times(1)).setMainAddress(false);
    }


}
