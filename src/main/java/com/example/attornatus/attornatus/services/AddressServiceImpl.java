package com.example.attornatus.attornatus.services;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.exeptions.ResourceNotFoundException;
import com.example.attornatus.attornatus.models.Address;
import com.example.attornatus.attornatus.repositorys.AddressRepository;
import com.example.attornatus.attornatus.repositorys.PersonRepository;
import com.example.attornatus.attornatus.services.autoupdate.AutoUpdateService;
import com.example.attornatus.attornatus.validators.AddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static com.example.attornatus.attornatus.mapper.UtilModelMapper.parseObject;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository repository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private AddressValidator validator;
    @Autowired
    private AutoUpdateService update;

    private Logger logger = Logger.getLogger(AddressService.class.getName());

    @Override
    public AddressDTO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        logger.info("Find one address by id: " + id);
        return parseObject(entity, AddressDTO.class);
    }

    @Override
    public AddressDTO create(Long idPerson, AddressDTO dto) throws Exception {
        var personEntity = personRepository.findById(idPerson)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        validator.create(dto, personService.findAddressesEntitiesByPersonId(personEntity.getId()));
        update.setOldMainAddressToFalse(idPerson, dto);

        var addressEntity = parseObject(dto, Address.class);

        addressEntity.setPerson(personEntity);

        repository.save(addressEntity);

        logger.info("Creating one AddressDTO");
        return parseObject(addressEntity, AddressDTO.class);
    }


    @Override
    public AddressDTO update(AddressDTO dto) {
        var entity = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        validator.update(dto);
        update.setOldMainAddressToFalse(entity.getPerson().getId(), dto);

        entity.setCep(dto.getCep());
        entity.setCity(dto.getCity());
        entity.setNumber(dto.getNumber());
        entity.setStreet(dto.getStreet());
        entity.setMainAddress(dto.isMainAddress());

        logger.info("Update one AddressDTO");
        return parseObject(repository.save(entity), AddressDTO.class);
    }

    @Override
    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        logger.info("Delete one AddressDTO");
        repository.delete(entity);
    }
}
