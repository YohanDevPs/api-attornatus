package com.example.attornatus.attornatus.mocks;

import com.example.attornatus.attornatus.dto.PersonDTO;
import com.example.attornatus.attornatus.models.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MockPerson {
    public Person mockEntity() {
        return mockEntity(0);
    }

    public PersonDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Person> mockEntityList() {
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<PersonDTO> mockVOList() {
        List<PersonDTO> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockDTO(i));
        }
        return persons;
    }

    public Person mockEntity(Integer number) {
        Person person = new Person();
        person.setId(number.longValue());
        person.setName("Name" + number);
        person.setBirthDay(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return person;
    }

    public PersonDTO mockDTO(Integer number) {
        PersonDTO person = new PersonDTO();
        person.setId(number.longValue());
        person.setName("Name" + number);
        person.setBirthDay(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return person;
    }
}
