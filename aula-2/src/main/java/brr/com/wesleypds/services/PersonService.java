package brr.com.wesleypds.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brr.com.wesleypds.controllers.exceptions.ResourceNotFoundException;
import brr.com.wesleypds.models.Person;
import brr.com.wesleypds.repositories.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person create(Person person) {

        logger.info("Creating one person!");

        return personRepository.save(person);
    }

    public Person update(Person person) {

        logger.info("Updating one person!");

        Person entity = personRepository.findById(person.getId())
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

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No records found this ID: %d", id)));
        
        personRepository.delete(entity);
    }

    public Person findById(Long id) {

        logger.info("Finding one person!");

        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No records found this ID: %d", id)));
    }

    public List<Person> findAll() {

        logger.info("Finding all people!");

        return personRepository.findAll();
    }

}
