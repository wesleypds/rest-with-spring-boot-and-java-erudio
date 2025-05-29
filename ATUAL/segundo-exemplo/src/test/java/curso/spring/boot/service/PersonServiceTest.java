package curso.spring.boot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import curso.spring.boot.exception.RequiredObjectIsNullException;
import curso.spring.boot.exception.ResourceNotFoundException;
import curso.spring.boot.model.entity.PersonEntity;
import curso.spring.boot.repository.PersonRepository;
import curso.spring.boot.unitetests.mapper.mocks.MockPerson;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        PersonEntity entity = input.mockEntity();

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        
        var result = service.findById(1L);
        
        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals(1, result.getId());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testFindByIdWithNotFoundPerson() {
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(1L);
        });

        String expectedMessage = "No records found for this ID!";
        String actualMessage = exception.getMessage();

        assertNotNull(exception);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreate() {
        PersonEntity entity = input.mockEntity();

        when(repository.save(entity)).thenReturn(entity);

        var result = service.create(entity);

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals(1, result.getId());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertNotNull(exception);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testUpdate() {
        PersonEntity entity = input.mockEntity();

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        var result = service.update(entity);

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals(1, result.getId());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertNotNull(exception);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testFindAll() {
        List<PersonEntity> entities = input.mockEntityList();

        when(repository.findAll()).thenReturn(entities);

        var result = service.findAll();

        assertNotNull(result);
        assertTrue(result.size() > 0);

        var objPositionZero = result.get(0);

        assertNotNull(objPositionZero.getId());
        assertEquals(1, objPositionZero.getId());
        assertEquals("First Name Test1", objPositionZero.getFirstName());
        assertEquals("Last Name Test1", objPositionZero.getLastName());
        assertEquals("Address Test1", objPositionZero.getAddress());
        assertEquals("Female", objPositionZero.getGender());

        var objPositionSeven = result.get(7);

        assertNotNull(objPositionSeven.getId());
        assertEquals(8, objPositionSeven.getId());
        assertEquals("First Name Test8", objPositionSeven.getFirstName());
        assertEquals("Last Name Test8", objPositionSeven.getLastName());
        assertEquals("Address Test8", objPositionSeven.getAddress());
        assertEquals("Male", objPositionSeven.getGender());

        var objPositionThirteen = result.get(13);

        assertNotNull(objPositionThirteen.getId());
        assertEquals(14, objPositionThirteen.getId());
        assertEquals("First Name Test14", objPositionThirteen.getFirstName());
        assertEquals("Last Name Test14", objPositionThirteen.getLastName());
        assertEquals("Address Test14", objPositionThirteen.getAddress());
        assertEquals("Male", objPositionThirteen.getGender());
    }

    @Test
    void testDelete() {
        PersonEntity entity = input.mockEntity();

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        
        service.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(PersonEntity.class));
        verifyNoMoreInteractions(repository);
    }
}
