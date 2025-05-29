package curso.spring.boot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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
        assertEquals(entity.getId(), result.getId());
    }

    @Test
    void testCreate() {

    }

    @Test
    void testDelete() {

    }

    @Test
    void testUpdate() {

    }

    @Test
    void testFindAll() {

    }
}
