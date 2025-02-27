package brr.com.wesleypds.services;

import java.util.ArrayList;
import java.util.List;
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

        person.setId(atomicLong.incrementAndGet());
        person.setFirstName("Wesley");
        person.setLastName("Pereira da Silva");
        person.setAddress("Rua Anchieta - N° 210, Areias Negras, Marataízes-ES");
        person.setGender("Masculino");

        return person;
    }

    public List<Person> findAll() {

        logger.info("Finding all people!");
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            people.add(person);
        }

        return people;

    }

    private Person mockPerson(int i) {
        Person person = new Person();

        person.setId(atomicLong.incrementAndGet());
        person.setFirstName("Person name " + (i+1));
        person.setLastName("Person lastName " + (i+1));
        person.setAddress("Some address in Brasil " + (i+1));
        person.setGender(i % 2 == 0 ? "Male " + (i+1) : "Female " + (i+1));

        return person;
    }

}
