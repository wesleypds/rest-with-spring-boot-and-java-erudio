package curso.spring.boot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void testFindAll() {
        List<BookEntity> entities = input.mockEntityList();
        List<BookDTO> dtos = input.mockDTOList();
        List<BookDTO> dtosWithHateoas = input.mockDTOWithHateoasList();

        when(service.findAll()).thenReturn(entities);
        when(mapper.parseListObject(entities, BookDTO.class)).thenReturn(dtos);
        when(service.addLinksHateoas(dtos)).thenReturn(dtosWithHateoas);

        var result = controller.findAll();

        assertNotNull(result);
        assertTrue(result.size() > 0);

        var objPositionZero = result.get(0);

        assertNotNull(objPositionZero.getId());
        assertNotNull(objPositionZero.getLinks());
        assertNotNull(objPositionZero.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/books/v1/1") &&
                        link.getType().equals("GET")));
        assertNotNull(objPositionZero.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/books/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(objPositionZero.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/books/v1/1") &&
                        link.getType().equals("DELETE")));

        assertEquals(1, objPositionZero.getId());
        assertEquals("Author Test1", objPositionZero.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), objPositionZero.getLaunchDate());
        assertEquals(BigDecimal.valueOf(1L), objPositionZero.getPrice());
        assertEquals("Title Test1", objPositionZero.getTitle());

        var objPositionSeven = result.get(7);

        assertNotNull(objPositionSeven.getId());
        assertNotNull(objPositionSeven.getLinks());
        assertNotNull(objPositionSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/books/v1/8") &&
                        link.getType().equals("GET")));
        assertNotNull(objPositionSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/books/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(objPositionSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/books/v1/8") &&
                        link.getType().equals("DELETE")));

        assertEquals(8, objPositionSeven.getId());
        assertEquals("Author Test8", objPositionSeven.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), objPositionSeven.getLaunchDate());
        assertEquals(BigDecimal.valueOf(8L), objPositionSeven.getPrice());
        assertEquals("Title Test8", objPositionSeven.getTitle());

        var objPositionThirteen = result.get(13);

        assertNotNull(objPositionThirteen.getId());
        assertNotNull(objPositionThirteen.getLinks());
        assertNotNull(objPositionThirteen.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/books/v1/14") &&
                        link.getType().equals("GET")));
        assertNotNull(objPositionThirteen.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/books/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(objPositionThirteen.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/books/v1/14") &&
                        link.getType().equals("DELETE")));

        assertEquals(14, objPositionThirteen.getId());
        assertEquals("Author Test14", objPositionThirteen.getAuthor());
        assertEquals(LocalDateTime.of(2025, 05, 29, 00, 00, 00), objPositionThirteen.getLaunchDate());
        assertEquals(BigDecimal.valueOf(14L), objPositionThirteen.getPrice());
        assertEquals("Title Test14", objPositionThirteen.getTitle());
    }

    @Test
    void testDelete() {
        service.delete(1L);

        verify(service, times(1)).delete(anyLong());
        verifyNoMoreInteractions(service);
    }
}
