package com.example.attornatus.attornatus.controller;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address/v1")
public class AddressController {

    @Autowired
    private AddressService service;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping(value = "/create/{idPerson}", consumes = {MediaType.APPLICATION_JSON_VALUE},  produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDTO create(@PathVariable(value = "idPerson") Long idPerson, @RequestBody AddressDTO dto) throws Exception {
        return service.create(idPerson, dto);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},  produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO update(@RequestBody AddressDTO dto) throws Exception {
        return service.update(dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) throws Exception {
        service.delete(id);
    }
}
