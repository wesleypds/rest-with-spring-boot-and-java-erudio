package brr.com.wesleypds.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brr.com.wesleypds.controllers.exceptions.ResourceNotFoundException;
import brr.com.wesleypds.data.vo.v1.PersonVO;
import brr.com.wesleypds.models.Person;
import brr.com.wesleypds.repositories.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonVO create(PersonVO person) {

        logger.info("Creating one person!");

        return personRepository.save(person);
    }

    public PersonVO update(PersonVO person) {

        logger.info("Updating one person!");

        PersonVO entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No records found this ID: %d", person.getId())));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");

        PersonVO entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No records found this ID: %d", id)));
        
        personRepository.delete(entity);
    }

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");

        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No records found this ID: %d", id)));
    }

    public List<PersonVO> findAll() {

        logger.info("Finding all people!");

        return personRepository.findAll();
    }

}
