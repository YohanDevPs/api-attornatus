package com.example.attornatus.attornatus.services;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.dto.PersonDTO;
import com.example.attornatus.attornatus.exeptions.RequiredObjectIsNullException;
import com.example.attornatus.attornatus.mocks.MockAddress;
import com.example.attornatus.attornatus.mocks.MockPerson;
import com.example.attornatus.attornatus.models.Address;
import com.example.attornatus.attornatus.models.Person;
import com.example.attornatus.attornatus.repositorys.AddressRepository;
import com.example.attornatus.attornatus.repositorys.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    MockPerson inputPerson; 
    MockAddress inputAddress;

    @InjectMocks
    private PersonServiceImpl service;
    @Mock
    private PersonRepository repository;
    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    void setUpMocks() {
        inputPerson = new MockPerson();
        inputAddress = new MockAddress();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindById() throws Exception {
        Person person = inputPerson.mockEntity(1);

        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.findById(1L);
        assertEquals(result.getClass() , PersonDTO.class);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Name1", result.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), result.getBirthDay());
    }


    @Test
    void testFindAll() throws Exception {
        var mockEntityList = inputPerson.mockEntityList();

        when(repository.findAll()).thenReturn(mockEntityList);
        var resultList = service.findAll();

        assertEquals(14, resultList.size());

        var firstPersonOfList = resultList.get(0);
        assertEquals(Long.valueOf(0), firstPersonOfList.getId());
        assertEquals("Name0", firstPersonOfList.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), firstPersonOfList.getBirthDay());

        var lastPersonOfList = resultList.get(13);
        assertEquals(Long.valueOf(13), lastPersonOfList.getId());
        assertEquals("Name13", lastPersonOfList.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), lastPersonOfList.getBirthDay());
    }

    @Test
    void testCreate() throws Exception {
        Person person = inputPerson.mockEntity(1);

        Person persisted = person;
        persisted.setId(1L);

        PersonDTO dto = inputPerson.mockDTO(1);
        dto.setId(1L);

        when(repository.save(person)).thenReturn(persisted);
        var result = service.create(dto);
        assertEquals(result.getClass() , PersonDTO.class);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Name1", result.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), result.getBirthDay());
    }

    @Test
    void testCreateIfNullPerson() throws Exception {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdate() throws Exception {
        Person entity = inputPerson.mockEntity(1);
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        PersonDTO dto = inputPerson.mockDTO(1);
        dto.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(dto);

        assertEquals(result.getClass() , PersonDTO.class);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Name1", result.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), result.getBirthDay());
    }

    @Test
    void testUpdateIfNullPerson() throws Exception {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDelete() throws Exception {
        Person person = inputPerson.mockEntity(1);

        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        service.delete(1L);
    }

    @Test
    void testGetAddressesEntitiesByPersonId() throws Exception {
        Person person = inputPerson.mockEntity(1);

        person.setId(1L);

        List<Address> addressList = inputAddress.mockEntityList();

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(addressRepository.findByPersonId(1L)).thenReturn(addressList);

        var result = service.getAddressesEntitiesByPersonId(1L);

        assertNotNull(result);
        assertEquals(9, result.size());

        var firstAddressOfList = result.get(0);
        assertEquals(Long.valueOf(0), firstAddressOfList.getId());
        assertEquals("12345-230", firstAddressOfList.getCEP());
        assertEquals("Salvador0", firstAddressOfList.getCity());
        assertEquals("Rua Manoel Gomes de Mendonca0", firstAddressOfList.getStreet());
        assertEquals(0, firstAddressOfList.getNumber());

        var lastAddressOfList = result.get(8);
        assertEquals(Long.valueOf(8), lastAddressOfList.getId());
        assertEquals("12345-238", lastAddressOfList.getCEP());
        assertEquals("Salvador8", lastAddressOfList.getCity());
        assertEquals("Rua Manoel Gomes de Mendonca8", lastAddressOfList.getStreet());
        assertEquals(8, lastAddressOfList.getNumber());
    }

    @Test
    public void testFindAddressByPersonId() {
        Long personId = 1L;
        var addressEntities = inputAddress.mockEntityList();
        var entityPerson = inputPerson.mockEntity();

        when(repository.findById(any())).thenReturn(Optional.ofNullable(entityPerson));
        when(service.getAddressesEntitiesByPersonId(any())).thenReturn(addressEntities);

        var expectedAddresses = inputAddress.mockDTOList();
        var actualAddresses = service.findAddressesByPersonId(personId);

        assertTrue(expectedAddresses.equals(actualAddresses));
    }


}
