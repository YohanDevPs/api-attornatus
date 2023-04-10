package com.example.attornatus.attornatus.services;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.dto.PersonDTO;
import com.example.attornatus.attornatus.exeptions.ResourceNotFoundException;
import com.example.attornatus.attornatus.mapper.UtilModelMapper;
import com.example.attornatus.attornatus.models.Address;
import com.example.attornatus.attornatus.models.Person;
import com.example.attornatus.attornatus.repositorys.AddressRepository;
import com.example.attornatus.attornatus.repositorys.PersonRepository;
import com.example.attornatus.attornatus.validators.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static com.example.attornatus.attornatus.mapper.UtilModelMapper.parseObject;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository repository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonValidator validator;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Override
    public List<PersonDTO> findAll() {
        var dtoList = UtilModelMapper.parseListObjects(repository.findAll(), PersonDTO.class);
        logger.info("Finding all people!");
        return dtoList;
    }

    @Override
    public PersonDTO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        logger.info("Find one PersonDTO");
        return parseObject(entity, PersonDTO.class);
    }

    @Override
    public PersonDTO create(PersonDTO dto) {
        validator.create(dto);
        var entity = parseObject(dto, Person.class);
        logger.info("Creating one PersonDTO");
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    @Override
    public PersonDTO update(PersonDTO dto) {
        validator.update(dto);

        var entity = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        entity.setName(dto.getName());
        entity.setBirthDay(dto.getBirthDay());

        logger.info("Update one PersonDTO");
        return parseObject(repository.save(entity), PersonDTO.class);
    }


    @Override
    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        logger.info("Delete one Person");
        repository.delete(entity);
    }

    @Override
    public List<Address> findAddressesEntitiesByPersonId(Long id) {
        var entityPerson = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        logger.info("Get all addresses of Person");
        return addressRepository.findByPersonId(entityPerson.getId());
    }

    @Override
    public List<AddressDTO> findAddressesByPersonId(Long id) {
        logger.info("Find all Addresses by person id: " + id);

        return UtilModelMapper.parseListObjects(findAddressesEntitiesByPersonId(id), AddressDTO.class);
    }

    @Override
    public AddressDTO findMainAddressByPersonId(Long id) {
        var entityMainAddress = findAddressesEntitiesByPersonId(id)
                .stream()
                .filter(p -> p.isMainAddress() == true)
                .findFirst()
                .orElse(null);

        logger.info("Find main Address by person id: " + id);
        return parseObject(entityMainAddress, AddressDTO.class);
    }
}
