package com.example.attornatus.attornatus.services;

import com.example.attornatus.attornatus.dto.PersonDTO;
import com.example.attornatus.attornatus.exeptions.ResourceNotFoundException;
import com.example.attornatus.attornatus.mapper.UtilModelMapper;
import com.example.attornatus.attornatus.models.Person;
import com.example.attornatus.attornatus.repositorys.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository repository;

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
        return UtilModelMapper.parseObject(entity, PersonDTO.class);
    }

    @Override
    public PersonDTO create(PersonDTO dto) {
        logger.info("Creating one PersonDTO");
        var entity = UtilModelMapper.parseObject(dto, Person.class);
        return UtilModelMapper.parseObject(repository.save(entity), PersonDTO.class);
    }

    @Override
    public PersonDTO update(PersonDTO dto) {
        logger.info("Update one PersonDTO");

        var entity = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        entity.setName(dto.getName());
        entity.setBirthDay(dto.getBirthDay());

        return UtilModelMapper.parseObject(repository.save(entity), PersonDTO.class);
    }


    @Override
    public void delete(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        repository.delete(entity);
    }
}
