package com.example.attornatus.attornatus.services;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.exeptions.ResourceNotFoundException;
import com.example.attornatus.attornatus.models.Address;
import com.example.attornatus.attornatus.repositorys.AddressRepository;
import com.example.attornatus.attornatus.repositorys.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static com.example.attornatus.attornatus.mapper.UtilModelMapper.parseListObjects;
import static com.example.attornatus.attornatus.mapper.UtilModelMapper.parseObject;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository repository;
    @Autowired
    private PersonRepository personRepository;

    private Logger logger = Logger.getLogger(AddressService.class.getName());

    @Override
    public List<AddressDTO> findAllByPersonId(Long id) {
        logger.info("find all Addresses by person id: " + id);
        var entityPerson = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        var setAddressesDto = repository.findByPersonId(entityPerson.getId());

        return parseListObjects(setAddressesDto, AddressDTO.class);
    }

    @Override
    public AddressDTO findById(Long id) {
        logger.info("find one address by id: " + id);
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        return parseObject(entity, AddressDTO.class);
    }

    @Override
    public AddressDTO create(Long idPerson, AddressDTO dto) {
        logger.info("Creating one AddressDTO");

        var personEntity = personRepository.findById(idPerson)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        var addressEntity = parseObject(dto, Address.class);

        addressEntity.setPerson(personEntity);
        repository.save(addressEntity);

        return parseObject(addressEntity, AddressDTO.class);
    }

    @Override
    public AddressDTO update(AddressDTO dto) {
        logger.info("Update one AddressDTO");
        var entity = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        entity.setCEP(dto.getCEP());
        entity.setCity(dto.getCity());
        entity.setNumber(dto.getNumber());
        entity.setStreet(dto.getStreet());

        return parseObject(repository.save(entity), AddressDTO.class);
    }

    @Override
    public void delete(Long id) {
        logger.info("delete one AddressDTO");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        repository.delete(entity);
    }
}
