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

import java.util.List;
import java.util.logging.Logger;

import static com.example.attornatus.attornatus.mapper.UtilModelMapper.parseListObjects;
import static com.example.attornatus.attornatus.mapper.UtilModelMapper.parseObject;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository repository;
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressValidator validator;

    @Autowired
    private AutoUpdateService update;
    private Logger logger = Logger.getLogger(AddressService.class.getName());

    @Override
    public List<AddressDTO> findAllByPersonId(Long id) {
        logger.info("find all Addresses by person id: " + id);
        List<Address> setAddressesDto = getAddressesByPersonId(id);

        return parseListObjects(setAddressesDto, AddressDTO.class);
    }
    @Override
    public List<Address> getAddressesByPersonId(Long id) {
        var entityPerson = personService.findById(id);
        var setAddressesDto = repository.findByPersonId(entityPerson.getId());
        return setAddressesDto;
    }

    @Override
    public AddressDTO findById(Long id) {
        logger.info("find one address by id: " + id);
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        return parseObject(entity, AddressDTO.class);
    }

    @Override
    public AddressDTO create(Long idPerson, AddressDTO dto) throws Exception {
        validator.cep(dto.getCEP());

        var personEntity = personRepository.findById(idPerson).get();

        update.setOldMainAddressToFalse(idPerson, dto);

        var addressEntity = parseObject(dto, Address.class);

        addressEntity.setPerson(personEntity);

        repository.save(addressEntity);

        logger.info("Creating one AddressDTO");
        return parseObject(addressEntity, AddressDTO.class);
    }


    @Override
    public AddressDTO update(AddressDTO dto) {
        validator.cep(dto.getCEP());
        var entity = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        var idPerson = entity.getPerson().getId();

        update.setOldMainAddressToFalse(idPerson, dto);

        entity.setCEP(dto.getCEP());
        entity.setCity(dto.getCity());
        entity.setNumber(dto.getNumber());
        entity.setStreet(dto.getStreet());
        entity.setMainAddress(dto.isMainAddress());

        logger.info("Update one AddressDTO");
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
