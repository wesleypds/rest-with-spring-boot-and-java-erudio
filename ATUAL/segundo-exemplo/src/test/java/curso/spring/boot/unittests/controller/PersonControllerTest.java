package curso.spring.boot.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

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
    void testFindAll() {
        List<PersonEntity> entities = input.mockEntityList();
        List<PersonDTO> dtos = input.mockDTOList();
        List<PersonDTO> dtosWithHateoas = input.mockDTOWithHateoasList();

        when(service.findAll()).thenReturn(entities);
        when(mapper.parseListObject(entities, PersonDTO.class)).thenReturn(dtos);
        when(service.addLinksHateoas(dtos)).thenReturn(dtosWithHateoas);

        var result = controller.findAll();

        assertNotNull(result);
        assertTrue(result.size() > 0);

        var objPositionZero = result.get(0);

        assertNotNull(objPositionZero.getId());
        assertNotNull(objPositionZero.getLinks());
        assertNotNull(objPositionZero.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/person/v1/1") &&
                        link.getType().equals("GET")));
        assertNotNull(objPositionZero.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/person/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(objPositionZero.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/person/v1/1") &&
                        link.getType().equals("DELETE")));

        assertEquals(1, objPositionZero.getId());
        assertEquals("First Name Test1", objPositionZero.getFirstName());
        assertEquals("Last Name Test1", objPositionZero.getLastName());
        assertEquals("Address Test1", objPositionZero.getAddress());
        assertEquals("Female", objPositionZero.getGender());

        var objPositionSeven = result.get(7);

        assertNotNull(objPositionSeven.getId());
        assertNotNull(objPositionSeven.getLinks());
        assertNotNull(objPositionSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/person/v1/8") &&
                        link.getType().equals("GET")));
        assertNotNull(objPositionSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/person/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(objPositionSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/person/v1/8") &&
                        link.getType().equals("DELETE")));

        assertEquals(8, objPositionSeven.getId());
        assertEquals("First Name Test8", objPositionSeven.getFirstName());
        assertEquals("Last Name Test8", objPositionSeven.getLastName());
        assertEquals("Address Test8", objPositionSeven.getAddress());
        assertEquals("Male", objPositionSeven.getGender());

        var objPositionThirteen = result.get(13);

        assertNotNull(objPositionThirteen.getId());
        assertNotNull(objPositionThirteen.getLinks());
        assertNotNull(objPositionThirteen.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findById") &&
                        link.getHref().contains("/api/person/v1/14") &&
                        link.getType().equals("GET")));
        assertNotNull(objPositionThirteen.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update") &&
                        link.getHref().contains("/api/person/v1") &&
                        link.getType().equals("PUT")));
        assertNotNull(objPositionThirteen.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete") &&
                        link.getHref().contains("/api/person/v1/14") &&
                        link.getType().equals("DELETE")));

        assertEquals(14, objPositionThirteen.getId());
        assertEquals("First Name Test14", objPositionThirteen.getFirstName());
        assertEquals("Last Name Test14", objPositionThirteen.getLastName());
        assertEquals("Address Test14", objPositionThirteen.getAddress());
        assertEquals("Male", objPositionThirteen.getGender());
    }

    @Test
    void testDelete() {
        service.delete(1L);

        verify(service, times(1)).delete(anyLong());
        verifyNoMoreInteractions(service);
    }
}
