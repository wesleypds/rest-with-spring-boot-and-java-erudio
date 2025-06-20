package curso.spring.boot.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import curso.spring.boot.controller.PersonController;
import curso.spring.boot.mocks.MockPerson;
import curso.spring.boot.model.dto.PersonDTO;
import curso.spring.boot.model.entity.PersonEntity;
import curso.spring.boot.model.mapper.ObjectMapper;
import curso.spring.boot.service.PersonService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    MockPerson input;

    @InjectMocks 
    private PersonController controller;

    @Mock 
    PersonService service;

    @Mock 
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        PersonEntity entity = input.mockEntity();
        PersonDTO dto = input.mockDTO();
        PersonDTO dtoWithHateoas = input.mockDTOWithHateoas();

        when(service.findById(entity.getId())).thenReturn(entity);
        when(mapper.parseObject(entity, PersonDTO.class)).thenReturn(dto);
        when(service.addLinksHateoas(dto)).thenReturn(dtoWithHateoas);

        var result = controller.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/person/v1/1") &&
                        link.getType().equals("GET")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/person/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/person/v1/1") &&
                        link.getType().equals("DELETE")));

        assertEquals(1, result.getId());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Female", result.getGender());

    }

    @Test
    void testCreate() {
        PersonEntity entity = input.mockEntity();
        PersonDTO dto = input.mockDTO();
        PersonDTO dtoWithHateoas = input.mockDTOWithHateoas();

        when(mapper.parseObject(dto, PersonEntity.class)).thenReturn(entity);
        when(service.create(entity)).thenReturn(entity);
        when(mapper.parseObject(entity, PersonDTO.class)).thenReturn(dto);
        when(service.addLinksHateoas(dto)).thenReturn(dtoWithHateoas);

        var result = controller.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/person/v1/1") &&
                        link.getType().equals("GET")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/person/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/person/v1/1") &&
                        link.getType().equals("DELETE")));

        assertEquals(1, result.getId());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdate() {
        PersonEntity entity = input.mockEntity();
        PersonDTO dto = input.mockDTO();
        PersonDTO dtoWithHateoas = input.mockDTOWithHateoas();

        when(mapper.parseObject(dto, PersonEntity.class)).thenReturn(entity);
        when(service.update(entity)).thenReturn(entity);
        when(mapper.parseObject(entity, PersonDTO.class)).thenReturn(dto);
        when(service.addLinksHateoas(dto)).thenReturn(dtoWithHateoas);

        var result = controller.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/person/v1/1") &&
                        link.getType().equals("GET")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/person/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/person/v1/1") &&
                        link.getType().equals("DELETE")));

        assertEquals(1, result.getId());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Address Test1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testDelete() {
        service.delete(1L);

        verify(service, times(1)).delete(anyLong());
        verifyNoMoreInteractions(service);
    }
}
