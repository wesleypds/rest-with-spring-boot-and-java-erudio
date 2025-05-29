package curso.spring.boot.unitetests.mapper;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import curso.spring.boot.model.dto.PersonDTO;
import curso.spring.boot.model.entity.PersonEntity;
import curso.spring.boot.model.mapper.ObjectMapper;
import curso.spring.boot.unitetests.mapper.mocks.MockPerson;


public class ObjectMapperTests {

    MockPerson inputObject;

    ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
        mapper = new ObjectMapper();
    }

    @Test
    public void parseEntityToDTOTest() {
        PersonDTO output = mapper.parseObject(inputObject.mockEntity(), PersonDTO.class);
        assertEquals(Long.valueOf(1L), output.getId());
        assertEquals("First Name Test1", output.getFirstName());
        assertEquals("Last Name Test1", output.getLastName());
        assertEquals("Address Test1", output.getAddress());
        assertEquals("Female", output.getGender());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        List<PersonDTO> outputList = mapper.parseListObject(inputObject.mockEntityList(), PersonDTO.class);
        PersonDTO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(1L), outputZero.getId());
        assertEquals("First Name Test1", outputZero.getFirstName());
        assertEquals("Last Name Test1", outputZero.getLastName());
        assertEquals("Address Test1", outputZero.getAddress());
        assertEquals("Female", outputZero.getGender());

        PersonDTO outputSeven = outputList.get(6);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());

        PersonDTO outputTwelve = outputList.get(11);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }

    @Test
    public void parseDTOToEntityTest() {
        PersonEntity output = mapper.parseObject(inputObject.mockDTO(), PersonEntity.class);
        assertEquals(Long.valueOf(1L), output.getId());
        assertEquals("First Name Test1", output.getFirstName());
        assertEquals("Last Name Test1", output.getLastName());
        assertEquals("Address Test1", output.getAddress());
        assertEquals("Female", output.getGender());
    }

    @Test
    public void parserDTOListToEntityListTest() {
        List<PersonEntity> outputList = mapper.parseListObject(inputObject.mockDTOList(), PersonEntity.class);
        PersonEntity outputZero = outputList.get(0);

        assertEquals(Long.valueOf(1L), outputZero.getId());
        assertEquals("First Name Test1", outputZero.getFirstName());
        assertEquals("Last Name Test1", outputZero.getLastName());
        assertEquals("Address Test1", outputZero.getAddress());
        assertEquals("Female", outputZero.getGender());

        PersonEntity outputSeven = outputList.get(6);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());

        PersonEntity outputTwelve = outputList.get(11);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }
}