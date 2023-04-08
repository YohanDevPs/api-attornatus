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
        logger.info("finding all people!");
        var dtoList = UtilModelMapper.parseListObjects(repository.findAll(), PersonDTO.class);
        return dtoList;
    }

    @Override
    public PersonDTO findById(Long id) {
        logger.info("find one PersonDTO");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        return parseObject(entity, PersonDTO.class);
    }

    @Override
    public PersonDTO create(PersonDTO dto) {
        validator.create(dto);
        logger.info("Creating one PersonDTO");
        var entity = parseObject(dto, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);

    }

    @Override
    public PersonDTO update(PersonDTO dto) {
        validator.update(dto);
        logger.info("Update one PersonDTO");

        var entity = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        entity.setName(dto.getName());
        entity.setBirthDay(dto.getBirthDay());

        return parseObject(repository.save(entity), PersonDTO.class);
    }


    @Override
    public void delete(Long id) {
        logger.info("Delete one Person");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        repository.delete(entity);
    }

    @Override
    public List<Address> findAddressesEntitiesByPersonId(Long id) {
        logger.info("get all addresses of Person");

        var entityPerson = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        return addressRepository.findByPersonId(entityPerson.getId());
    }

    @Override
    public List<AddressDTO> findAddressesByPersonId(Long id) {
        logger.info("find all Addresses by person id: " + id);

        return UtilModelMapper.parseListObjects(findAddressesEntitiesByPersonId(id), AddressDTO.class);
    }

    @Override
    public AddressDTO findMainAddressByPersonId(Long id) {
        var entityMainAddress = findAddressesEntitiesByPersonId(id)
                .stream()
                .filter(p -> p.isMainAddress() == true)
                .findFirst()
                .orElse(null);

        return parseObject(entityMainAddress, AddressDTO.class);
    }
}
