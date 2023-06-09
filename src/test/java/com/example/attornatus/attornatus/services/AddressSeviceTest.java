package com.example.attornatus.attornatus.services;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.mocks.MockAddress;
import com.example.attornatus.attornatus.mocks.MockPerson;
import com.example.attornatus.attornatus.models.Address;
import com.example.attornatus.attornatus.repositorys.AddressRepository;
import com.example.attornatus.attornatus.repositorys.PersonRepository;
import com.example.attornatus.attornatus.services.autoupdate.AutoUpdateService;
import com.example.attornatus.attornatus.validators.AddressValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class AddressSeviceTest {

    public static final long ID = 1L;
    MockPerson inputPerson;
    MockAddress inputAddress;

    @InjectMocks
    private AddressServiceImpl service;
    @Mock
    private AddressRepository repository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private AutoUpdateService update;
    @Mock
    private PersonService personService;
    @Mock
    private AddressValidator validator;

    @BeforeEach
    void setUpMocks() {
        validator = new AddressValidator();
        inputPerson = new MockPerson();
        inputAddress = new MockAddress();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        var entity = inputAddress.mockEntity(1);
        entity.setId(ID);

        when(repository.findById(ID)).thenReturn(Optional.ofNullable(entity));

        var result = service.findById(ID);
        assertEquals(result.getClass() , AddressDTO.class);
        assertNotNull(result);

        assertEquals(ID, result.getId());
        assertEquals("12345-231", result.getCep());
        assertEquals("Salvador1", result.getCity());
        assertEquals("Rua Manoel Gomes de Mendonca1", result.getStreet());
        assertEquals(1, result.getNumber());
    }

    @Test
    public void testCreate() throws Exception {
        var personEntity = inputPerson.mockEntity(1);
        personEntity.setId(ID);

        var addressDTO = inputAddress.mockDTO(1);
        var addressesToVerifyRepetition = inputAddress.mockEntityList();

        when(personRepository.findById(ID)).thenReturn(Optional.of(personEntity));
        doNothing().when(validator).create(any(AddressDTO.class), any());
        when(personService.findAddressesEntitiesByPersonId(ID)).thenReturn(addressesToVerifyRepetition);
        doNothing().when(update).setOldMainAddressToFalse(ID, addressDTO);

        var result = service.create(ID, addressDTO);

        ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
        verify(repository, times(1)).save(addressCaptor.capture());

        assertNotNull(result);
        assertEquals(ID,  result.getId());
        assertEquals("12345-231", result.getCep());
        assertEquals("Salvador1", result.getCity());
        assertEquals("Rua Manoel Gomes de Mendonca1", result.getStreet());
        assertEquals(1, result.getNumber());
    }

    @Test
    public void testUpdate() throws Exception {
        var addressDTO = inputAddress.mockDTO(4);
        var addressEntity = inputAddress.mockEntity(1);
        var personEntity = inputPerson.mockEntity(1);
        personEntity.setId(ID);

        addressEntity.setPerson(personEntity);

        when(repository.findById(addressDTO.getId())).thenReturn(Optional.ofNullable(addressEntity));
        doNothing().when(update).setOldMainAddressToFalse(ID, addressDTO);

        when(repository.save(addressEntity)).thenReturn(addressEntity);

        var result = service.update(addressDTO);

        assertNotNull(result);

        assertEquals(addressEntity.getCep(), result.getCep());
        assertEquals(addressEntity.getCity(), result.getCity());
        assertEquals(addressEntity.getNumber(), result.getNumber());
        assertEquals(addressEntity.getStreet(), result.getStreet());
        assertEquals(addressEntity.isMainAddress(), result.isMainAddress());
    }

    @Test
    public void testDelete() throws Exception {
        var addressEntity = inputAddress.mockEntity(1);

        when(repository.findById(ID)).thenReturn(Optional.ofNullable(addressEntity));
        service.delete(ID);
        verify(repository, times(1)).delete(addressEntity);
    }
}
