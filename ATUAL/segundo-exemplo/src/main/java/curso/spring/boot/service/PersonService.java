package curso.spring.boot.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.spring.boot.exception.ResourceNotFoundException;
import curso.spring.boot.model.entity.PersonEntity;
import curso.spring.boot.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public List<PersonEntity> findAll() {
        
        logger.info("Finding all people!");

        return repository.findAll();
    }

    public PersonEntity findById(Long id) {

        logger.info("Finding one person");

        return repository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public PersonEntity create(PersonEntity entity) {

        logger.info("Creating one perso");

        return repository.save(entity);
    }

    public PersonEntity update(PersonEntity entity) {

        logger.info("Updating one perso");

        PersonEntity e = findById(entity.getId());

        e.setFirstName(entity.getFirstName());
        e.setLastName(entity.getLastName());
        e.setAddress(entity.getAddress());
        e.setGender(entity.getGender());

        return repository.save(e);
    }

    public void delete(Long id) {

        logger.info("Deleting one person");

        PersonEntity entity = findById(id);

        repository.delete(entity);
    }

}
