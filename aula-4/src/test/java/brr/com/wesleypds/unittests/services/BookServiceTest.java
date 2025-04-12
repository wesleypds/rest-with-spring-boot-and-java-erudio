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

import brr.com.wesleypds.data.vo.BookVO;
import brr.com.wesleypds.exceptions.RequiredIsNullException;
import brr.com.wesleypds.models.Book;
import brr.com.wesleypds.repositories.BookRepository;
import brr.com.wesleypds.services.BookService;
import brr.com.wesleypds.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    MockBook input;

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @BeforeEach
    void setUp() throws Exception {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Book entity = input.mockEntity(1);
        entity.setId(null);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals(entity.getAuthor(), result.getAuthor());
        assertEquals(entity.getLaunchDate(), result.getLaunchDate());
        assertEquals(entity.getPrice(), result.getPrice());
        assertEquals(entity.getTitle(), result.getTitle());
    }

    @Test
    void testCreateWithNullBook() {
        Exception exception = assertThrows(RequiredIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDelete() {
        Book entity = input.mockEntity(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }

    @Test
    void testFindById() {
        Book entity = input.mockEntity(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals(entity.getAuthor(), result.getAuthor());
        assertEquals(entity.getLaunchDate(), result.getLaunchDate());
        assertEquals(entity.getPrice(), result.getPrice());
        assertEquals(entity.getTitle(), result.getTitle());
    }

    @Test
    void testUpdate() {
        Book entity = input.mockEntity(1);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals(entity.getAuthor(), result.getAuthor());
        assertEquals(entity.getLaunchDate(), result.getLaunchDate());
        assertEquals(entity.getPrice(), result.getPrice());
        assertEquals(entity.getTitle(), result.getTitle());
    }

    @Test
    void testUpdateWithNullBook() {
        Exception exception = assertThrows(RequiredIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
