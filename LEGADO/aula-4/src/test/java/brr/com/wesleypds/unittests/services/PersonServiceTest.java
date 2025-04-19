package brr.com.wesleypds.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import brr.com.wesleypds.data.vo.PersonVO;
import brr.com.wesleypds.exceptions.RequiredIsNullException;
import brr.com.wesleypds.models.Person;
import brr.com.wesleypds.repositories.PersonRepository;
import brr.com.wesleypds.services.PersonService;
import brr.com.wesleypds.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    MockPerson input;

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    @BeforeEach
    void setUp() throws Exception {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Person entity = input.mockEntity(1);
        entity.setId(null);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/people/v1/1>;rel=\"self\"]"));
        assertEquals(entity.getFirstName(), result.getFirstName());
        assertEquals(entity.getLastName(), result.getLastName());
        assertEquals(entity.getAddress(), result.getAddress());
        assertEquals(entity.getGender(), result.getGender());
        assertEquals(entity.getEnabled(), result.getEnabled());
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDelete() {
        Person entity = input.mockEntity(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }

    @Test
    void testFindById() {
        Person entity = input.mockEntity(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/people/v1/1>;rel=\"self\"]"));
        assertEquals(entity.getFirstName(), result.getFirstName());
        assertEquals(entity.getLastName(), result.getLastName());
        assertEquals(entity.getAddress(), result.getAddress());
        assertEquals(entity.getGender(), result.getGender());
        assertEquals(entity.getEnabled(), result.getEnabled());
    }

    @Test
    void testUpdate() {
        Person entity = input.mockEntity(1);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/people/v1/1>;rel=\"self\"]"));
        assertEquals(entity.getFirstName(), result.getFirstName());
        assertEquals(entity.getLastName(), result.getLastName());
        assertEquals(entity.getAddress(), result.getAddress());
        assertEquals(entity.getGender(), result.getGender());
        assertEquals(entity.getEnabled(), result.getEnabled());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
