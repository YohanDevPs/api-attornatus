package com.example.attornatus.attornatus.controllers;

import com.example.attornatus.attornatus.controller.PersonController;
import com.example.attornatus.attornatus.dto.PersonDTO;
import com.example.attornatus.attornatus.mocks.MockAddress;
import com.example.attornatus.attornatus.mocks.MockPerson;
import com.example.attornatus.attornatus.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    public static final long ID = 1L;
    @Autowired
    MockMvc mockMvc;
    MockPerson inputPerson;
    MockAddress inputAddress;
    ObjectMapper objectMapper;
    @Autowired
    private PersonController personController;
    @MockBean
    private PersonService personService;

    private String url = "/api/person/v1";

    @BeforeEach
    public void setup() {
        inputPerson = new MockPerson();
        inputAddress = new MockAddress();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(personController)
                .setControllerAdvice()
                .build();
    }

    @Test
    public void testEndpointFindAll() throws Exception {
        var personDTOList = inputPerson.mockDTOList();

        Mockito.when(personService.findAll()).thenReturn(personDTOList);

        var jsonPerson = objectMapper.writeValueAsString(personDTOList);

        this.mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonPerson));

        personController.findAll();
    }

    @Test
    public void testEndpointFindById() throws Exception {
        var personDTO = inputPerson.mockDTO(1);

        Mockito.when(personService.findById(ID)).thenReturn(personDTO);

        var jsonPerson = objectMapper.writeValueAsString(personDTO);

        this.mockMvc.perform(get(url+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.birthDay").value("31-12-1998"));

        personController.findById(ID);
    }

    @Test
    public void testEndpointCreatePerson() throws Exception {
        var personDTO = inputPerson.mockDTO(1);

        Mockito.when(personService.create(Mockito.any())).thenReturn(personDTO);

        var jsonPerson = objectMapper.writeValueAsString(personDTO);

        this.mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.birthDay").value("31-12-1998"));

        personController.create(personDTO);
    }

    @Test
    public void testEndpointUpdate() throws Exception {
        PersonDTO personDTO = inputPerson.mockDTO(1);
        when(personService.update(any(PersonDTO.class))).thenReturn(personDTO);

        var jsonPerson = objectMapper.writeValueAsString(personDTO);

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonPerson))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.birthDay").value("31-12-1998"));

        personController.update(personDTO);
    }

    @Test
    public void testEndpointDeletionByIdPerson() throws Exception {
        doNothing().when(personService).delete(ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        personController.delete(ID);
    }

    @Test
    public void testEndpointFindAddressesByPersonId() throws Exception {
        var addressDTOList = inputAddress.mockDTOList();

        when(personService.findAddressesByPersonId(ID)).thenReturn(addressDTOList);

        var jsonPerson = objectMapper.writeValueAsString(addressDTOList);

        this.mockMvc.perform(get(url+"/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonPerson))
                .andExpect(jsonPath("$[1].id").value(ID))
                .andExpect(jsonPath("$[1].cep").value("12345-231"))
                .andExpect(jsonPath("$[1].city").value("Salvador1"))
                .andExpect(jsonPath("$[1].street").value("Rua Manoel Gomes de Mendonca1"))
                .andExpect(jsonPath("$[1].number").value("1"));

        personController.findAddressesByPersonId(ID);
    }

    @Test
    public void testEndpointFindMainAddress() throws Exception {
        var mainAddressDTO = inputAddress.mockDTO(1);
        mainAddressDTO.setMainAddress(true);

        Mockito.when(personService.findMainAddressByPersonId(ID)).thenReturn(mainAddressDTO);

        var jsonAddressMainDTO = objectMapper.writeValueAsString(mainAddressDTO);

        this.mockMvc.perform(get(url+"/mainAdress/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAddressMainDTO)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.cep").value("12345-231"))
                .andExpect(jsonPath("$.city").value("Salvador1"))
                .andExpect(jsonPath("$.street").value("Rua Manoel Gomes de Mendonca1"))
                .andExpect(jsonPath("$.number").value("1"))
                .andExpect(jsonPath("$.mainAddress").value("true"));

        personController.findMainAddress(ID);
    }
}
