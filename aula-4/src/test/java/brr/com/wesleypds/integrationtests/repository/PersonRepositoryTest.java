package brr.com.wesleypds.integrationtests.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import brr.com.wesleypds.integrationtests.testcontainers.AbstractIntegrationTest;
import brr.com.wesleypds.models.Person;
import brr.com.wesleypds.repositories.PersonRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository repository;

    private static Person person;

    @BeforeAll
    static void setUp() {
        person = new Person();
    }

    @Test
    @Order(0)
    public void testFindPeopleByName() throws JsonMappingException, JsonProcessingException {

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "firstName"));
        person = repository.findPeopleByName("car", pageable).getContent().get(0);

        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());
        assertNotNull(person.getEnabled());

        assertEquals(9L, person.getId());
        assertEquals("Ricardo", person.getFirstName());
        assertEquals("Gomes", person.getLastName());
        assertEquals("Avenida das Rosas, 2021", person.getAddress());
        assertEquals("Masculino", person.getGender());
        assertEquals(true, person.getEnabled());

    }

    @Test
    @Order(1)
    public void testPersonDisable() throws JsonMappingException, JsonProcessingException {

        repository.personDisable(person.getId());
        
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "firstName"));
        person = repository.findPeopleByName("car", pageable).getContent().get(0);

        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());
        assertNotNull(person.getEnabled());

        assertEquals(9L, person.getId());
        assertEquals("Ricardo", person.getFirstName());
        assertEquals("Gomes", person.getLastName());
        assertEquals("Avenida das Rosas, 2021", person.getAddress());
        assertEquals("Masculino", person.getGender());
        assertEquals(false, person.getEnabled());

    }

}
