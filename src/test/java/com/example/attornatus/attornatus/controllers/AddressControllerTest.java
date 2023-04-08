package com.example.attornatus.attornatus.controllers;

import com.example.attornatus.attornatus.controller.AddressController;
import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.mocks.MockAddress;
import com.example.attornatus.attornatus.mocks.MockPerson;
import com.example.attornatus.attornatus.services.AddressService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {

    public static final long ID = 1L;
    private static final String URL = "/api/address/v1";
    @Autowired
    MockMvc mockMvc;
    MockPerson inputPerson;
    MockAddress inputAddress;
    ObjectMapper objectMapper;
    @Autowired
    private AddressController addressController;
    @MockBean
    private AddressService addressService;


    @BeforeEach
    public void setupEndpoint() {
        inputPerson = new MockPerson();
        inputAddress = new MockAddress();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(addressController)
                .setControllerAdvice()
                .build();
    }

    @Test
    public void testEndpointCreate() throws Exception {
        var addressDTO = inputAddress.mockDTO(1);

        when(addressService.create(any(), any(AddressDTO.class))).thenReturn(addressDTO);

        var jsonAddress = objectMapper.writeValueAsString(addressDTO);

        this.mockMvc.perform(post(URL + "/create/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAddress)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cep").value("12345-231"))
                .andExpect(jsonPath("$.city").value("Salvador1"))
                .andExpect(jsonPath("$.street").value("Rua Manoel Gomes de Mendonca1"))
                .andExpect(jsonPath("$.number").value(1));

        addressController.create(ID, addressDTO);
    }

    @Test
    public void testEndpointFindById() throws Exception {
        var addressDTO = inputAddress.mockDTO(1);

        Mockito.when(addressService.findById(ID)).thenReturn(addressDTO);

        var jsonAddress = objectMapper.writeValueAsString(addressDTO);

        this.mockMvc.perform(get(URL+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAddress)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cep").value("12345-231"))
                .andExpect(jsonPath("$.city").value("Salvador1"))
                .andExpect(jsonPath("$.street").value("Rua Manoel Gomes de Mendonca1"))
                .andExpect(jsonPath("$.number").value(1));

        addressController.findById(ID);
    }

    @Test
    public void testEndpointUpdate() throws Exception {
        var addressDTO = inputAddress.mockDTO(1);
        when(addressService.update(any(AddressDTO.class))).thenReturn(addressDTO);

        var jsonAddress = objectMapper.writeValueAsString(addressDTO);
        // when
        mockMvc.perform(put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAddress))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonAddress))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cep").value("12345-231"))
                .andExpect(jsonPath("$.city").value("Salvador1"))
                .andExpect(jsonPath("$.street").value("Rua Manoel Gomes de Mendonca1"))
                .andExpect(jsonPath("$.number").value(1));

        addressController.update(addressDTO);
    }

    @Test
    public void testEndpointDeletionByAddressId() throws Exception {
        doNothing().when(addressService).delete(ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL+String.format("/%d", ID))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        addressController.delete(ID);
    }
}
