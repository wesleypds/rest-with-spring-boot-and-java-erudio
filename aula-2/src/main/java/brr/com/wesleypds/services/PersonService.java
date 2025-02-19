package brr.com.wesleypds.services;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import brr.com.wesleypds.models.Person;

@Service
public class PersonService {

    private final AtomicLong atomicLong = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(String id) {

        logger.info("Finding one person!");
        Person person = new Person();

        mockPerson(person);

        return person;
    }

    private void mockPerson(Person person) {
        person.setId(atomicLong.incrementAndGet());
        person.setFirstName("Wesley");
        person.setLastName("Pereira da Silva");
        person.setAddress("Rua Anchieta - N° 210, Areias Negras, Marataízes-ES");
        person.setGender("Masculino");
    }

}
