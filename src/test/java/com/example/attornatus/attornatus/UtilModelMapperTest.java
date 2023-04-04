package com.example.attornatus.attornatus;

import com.example.attornatus.attornatus.dto.PersonDTO;
import com.example.attornatus.attornatus.mapper.UtilModelMapper;
import com.example.attornatus.attornatus.mocks.MockPerson;
import com.example.attornatus.attornatus.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilModelMapperTest {

    MockPerson inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToDTOTest() {
        PersonDTO output = UtilModelMapper.parseObject(inputObject.mockEntity(), PersonDTO.class);
        assertEquals(Long.valueOf(0), output.getId());
        assertEquals("Name0", output.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), output.getBirthDay());
    }

    @Test
    public void parseDTOToEntity() {
        Person output = UtilModelMapper.parseObject(inputObject.mockEntity(), Person.class);
        assertEquals(Long.valueOf(0), output.getId());
        assertEquals("Name0", output.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), output.getBirthDay());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<PersonDTO> outputList = UtilModelMapper.parseListObjects(inputObject.mockEntityList(), PersonDTO.class);

        var outputZero = outputList.get(0);
        assertEquals(Long.valueOf(0), outputZero.getId());
        assertEquals("Name0", outputZero.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), outputZero.getBirthDay());

        var outputSeven = outputList.get(7);
        assertEquals(Long.valueOf(7), outputSeven.getId());
        assertEquals("Name7", outputSeven.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), outputSeven.getBirthDay());

        var outputTwelve = outputList.get(12);
        assertEquals(Long.valueOf(12), outputTwelve.getId());
        assertEquals("Name12", outputTwelve.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), outputTwelve.getBirthDay());
    }

    @Test
    public void parseDTOListToEntityListTest() {
        List<Person> outputList = UtilModelMapper.parseListObjects(inputObject.mockEntityList(), Person.class);

        var outputZero = outputList.get(0);
        assertEquals(Long.valueOf(0), outputZero.getId());
        assertEquals("Name0", outputZero.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), outputZero.getBirthDay());

        var outputSeven = outputList.get(7);
        assertEquals(Long.valueOf(7), outputSeven.getId());
        assertEquals("Name7", outputSeven.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), outputSeven.getBirthDay());

        var outputTwelve = outputList.get(12);
        assertEquals(Long.valueOf(12), outputTwelve.getId());
        assertEquals("Name12", outputTwelve.getName());
        assertEquals(LocalDate.parse("31-12-1998", DateTimeFormatter.ofPattern("dd-MM-yyyy")), outputTwelve.getBirthDay());
    }

}
