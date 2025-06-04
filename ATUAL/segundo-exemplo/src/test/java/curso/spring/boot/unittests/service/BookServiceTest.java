package curso.spring.boot.unittests.service;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import curso.spring.boot.mocks.MockBook;
import curso.spring.boot.model.entity.BookEntity;
import curso.spring.boot.repository.BookRepository;
import curso.spring.boot.service.BookService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        BookEntity entity = input.mockEntity();

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        
        var result = service.findById(1L);
        
        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals(1, result.getId());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), result.getLaunchDate());
        assertEquals(BigDecimal.valueOf(1L), result.getPrice());
        assertEquals("Title Test1", result.getTitle());
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
        BookEntity entity = input.mockEntity();

        when(repository.save(entity)).thenReturn(entity);

        var result = service.create(entity);

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals(1, result.getId());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), result.getLaunchDate());
        assertEquals(BigDecimal.valueOf(1L), result.getPrice());
        assertEquals("Title Test1", result.getTitle());
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
        BookEntity entity = input.mockEntity();

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        var result = service.update(entity);

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals(1, result.getId());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), result.getLaunchDate());
        assertEquals(BigDecimal.valueOf(1L), result.getPrice());
        assertEquals("Title Test1", result.getTitle());
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
        List<BookEntity> entities = input.mockEntityList();

        when(repository.findAll()).thenReturn(entities);

        var result = service.findAll();

        assertNotNull(result);
        assertTrue(result.size() > 0);

        var objPositionZero = result.get(0);

        assertNotNull(objPositionZero.getId());
        assertEquals(1, objPositionZero.getId());
        assertEquals("Author Test1", objPositionZero.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), objPositionZero.getLaunchDate());
        assertEquals(BigDecimal.valueOf(1L), objPositionZero.getPrice());
        assertEquals("Title Test1", objPositionZero.getTitle());

        var objPositionSeven = result.get(7);

        assertNotNull(objPositionSeven.getId());
        assertEquals(8, objPositionSeven.getId());
        assertEquals("Author Test8", objPositionSeven.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), objPositionSeven.getLaunchDate());
        assertEquals(BigDecimal.valueOf(8L), objPositionSeven.getPrice());
        assertEquals("Title Test8", objPositionSeven.getTitle());

        var objPositionThirteen = result.get(13);

        assertNotNull(objPositionThirteen.getId());
        assertEquals(14, objPositionThirteen.getId());
        assertEquals("Author Test14", objPositionThirteen.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), objPositionThirteen.getLaunchDate());
        assertEquals(BigDecimal.valueOf(14L), objPositionThirteen.getPrice());
        assertEquals("Title Test14", objPositionThirteen.getTitle());
    }

    @Test
    void testDelete() {
        BookEntity entity = input.mockEntity();

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        
        service.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(BookEntity.class));
        verifyNoMoreInteractions(repository);
    }
}
