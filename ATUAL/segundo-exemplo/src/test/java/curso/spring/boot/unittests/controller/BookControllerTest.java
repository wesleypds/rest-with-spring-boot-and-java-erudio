package curso.spring.boot.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import curso.spring.boot.controller.BookController;
import curso.spring.boot.mocks.MockBook;
import curso.spring.boot.model.dto.BookDTO;
import curso.spring.boot.model.entity.BookEntity;
import curso.spring.boot.model.mapper.ObjectMapper;
import curso.spring.boot.service.BookService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    MockBook input;

    @InjectMocks 
    private BookController controller;

    @Mock 
    BookService service;

    @Mock 
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        BookEntity entity = input.mockEntity();
        BookDTO dto = input.mockDTO();
        BookDTO dtoWithHateoas = input.mockDTOWithHateoas();

        when(service.findById(entity.getId())).thenReturn(entity);
        when(mapper.parseObject(entity, BookDTO.class)).thenReturn(dto);
        when(service.addLinksHateoas(dto)).thenReturn(dtoWithHateoas);

        var result = controller.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/books/v1/1") &&
                        link.getType().equals("GET")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/books/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/books/v1/1") &&
                        link.getType().equals("DELETE")));

        assertEquals(1, result.getId());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), result.getLaunchDate());
        assertEquals(BigDecimal.valueOf(1L), result.getPrice());
        assertEquals("Title Test1", result.getTitle());

    }

    @Test
    void testCreate() {
        BookEntity entity = input.mockEntity();
        BookDTO dto = input.mockDTO();
        BookDTO dtoWithHateoas = input.mockDTOWithHateoas();

        when(mapper.parseObject(dto, BookEntity.class)).thenReturn(entity);
        when(service.create(entity)).thenReturn(entity);
        when(mapper.parseObject(entity, BookDTO.class)).thenReturn(dto);
        when(service.addLinksHateoas(dto)).thenReturn(dtoWithHateoas);

        var result = controller.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/books/v1/1") &&
                        link.getType().equals("GET")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/books/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/books/v1/1") &&
                        link.getType().equals("DELETE")));

        assertEquals(1, result.getId());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), result.getLaunchDate());
        assertEquals(BigDecimal.valueOf(1L), result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void testUpdate() {
        BookEntity entity = input.mockEntity();
        BookDTO dto = input.mockDTO();
        BookDTO dtoWithHateoas = input.mockDTOWithHateoas();

        when(mapper.parseObject(dto, BookEntity.class)).thenReturn(entity);
        when(service.update(entity)).thenReturn(entity);
        when(mapper.parseObject(entity, BookDTO.class)).thenReturn(dto);
        when(service.addLinksHateoas(dto)).thenReturn(dtoWithHateoas);

        var result = controller.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/books/v1/1") &&
                        link.getType().equals("GET")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/books/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/books/v1/1") &&
                        link.getType().equals("DELETE")));

        assertEquals(1, result.getId());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), result.getLaunchDate());
        assertEquals(BigDecimal.valueOf(1L), result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void testDelete() {
        service.delete(1L);

        verify(service, times(1)).delete(anyLong());
        verifyNoMoreInteractions(service);
    }
}
