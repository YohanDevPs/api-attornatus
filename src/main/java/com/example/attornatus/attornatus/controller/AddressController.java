package com.example.attornatus.attornatus.controller;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address/v1")
@Tag(name = "Address", description = "Endpoints for managing addresses")
public class AddressController {

    @Autowired
    private AddressService service;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Finds a Address", description = "Finds a Address passing the id of the person", tags = {"Address"},
            responses = @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AddressDTO.class))))
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO findById(@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @PostMapping(value = "/create/{idPerson}", consumes = {MediaType.APPLICATION_JSON_VALUE},  produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Create a Address",
            description = "Create a Address by passing an address in json format and the id of the person", tags = {"Address"},
            responses = @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = AddressDTO.class))))
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDTO create(@PathVariable(value = "idPerson") Long idPerson, @RequestBody AddressDTO dto) throws Exception {
        return service.create(idPerson, dto);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},  produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update a Address", description = "Update a Address by passing a JSON", tags = {"Address"},
            responses = @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AddressDTO.class))))
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO update(@RequestBody AddressDTO dto) throws Exception {
        return service.update(dto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a Person", description = "Delete a Address by passing id", tags = {"Address"},
            responses = @ApiResponse(description = "No Content", responseCode = "204", content = @Content))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) throws Exception {
        service.delete(id);
    }
}
