package com.example.attornatus.attornatus.services;

import com.example.attornatus.attornatus.dto.PersonDTO;
import com.example.attornatus.attornatus.mocks.MockPerson;
import com.example.attornatus.attornatus.models.Person;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonServiceImpl service;

    @Mock
    private PersonRepository repository;

    @BeforeEach
    void setUpMocks() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindById() throws Exception {
        Person person = input.mockEntity(1);
        System.out.println(person);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.findById(1L);
        assertEquals(result.getClass() , PersonDTO.class);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Name1", result.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), result.getBirthDay());
    }
}
