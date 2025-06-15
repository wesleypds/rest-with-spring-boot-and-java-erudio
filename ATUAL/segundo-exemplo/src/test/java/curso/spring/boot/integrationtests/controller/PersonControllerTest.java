package curso.spring.boot.integrationtests.controller;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import curso.spring.boot.config.TestConfigs;
import curso.spring.boot.integrationtests.dto.PersonDTO;
import curso.spring.boot.integrationtests.testcontainer.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static PersonDTO person;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        person = new PersonDTO();
    }

    @Test
    @Order(1)
    void testCreate() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                        .setBasePath("/api/people/v1")
                        .setPort(TestConfigs.SERVER_PORT)
                            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                        .build();

        var result =given(specification)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(person)
                    .when()
                        .post()
                    .then()
                        .statusCode(200)
                    .extract()
                        .body()
                            .asString();

		person = mapper.readValue(result, PersonDTO.class);

        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());
        assertEquals("Wesley", person.getFirstName());
        assertEquals("Pereira da Silva", person.getLastName());
        assertEquals("Marataízes - Espírito Santo - Brasil", person.getAddress());
        assertEquals("Male", person.getGender());
    }

    @Test
    @Order(2)
    void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {

        specification = new RequestSpecBuilder()
                        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_GOOGLE)
                        .setBasePath("/api/people/v1")
                        .setPort(TestConfigs.SERVER_PORT)
                            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                        .build();

        var result =given(specification)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(person)
                    .when()
                        .post()
                    .then()
                        .statusCode(403)
                    .extract()
                        .body()
                            .asString();

        assertEquals("Invalid CORS request", result);
    }

    @Test
    @Order(3)
    void testFindById() throws JsonMappingException, JsonProcessingException {
        specification = new RequestSpecBuilder()
                        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                        .setBasePath("/api/people/v1")
                        .setPort(TestConfigs.SERVER_PORT)
                            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                        .build();

        var result =given(specification)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .pathParam("id", person.getId())
                    .when()
                        .get("/{id}")
                    .then()
                        .statusCode(200)
                    .extract()
                        .body()
                            .asString();

		person = mapper.readValue(result, PersonDTO.class);

        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());
        assertEquals("Wesley", person.getFirstName());
        assertEquals("Pereira da Silva", person.getLastName());
        assertEquals("Marataízes - Espírito Santo - Brasil", person.getAddress());
        assertEquals("Male", person.getGender());
    }

    @Test
    @Order(4)
    void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
        specification = new RequestSpecBuilder()
                        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_GOOGLE)
                        .setBasePath("/api/people/v1")
                        .setPort(TestConfigs.SERVER_PORT)
                            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                        .build();

        var result =given(specification)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .pathParam("id", person.getId())
                    .when()
                        .get("/{id}")
                    .then()
                        .statusCode(403)
                    .extract()
                        .body()
                            .asString();

		assertEquals("Invalid CORS request", result);
    }

    private void mockPerson() {
        person.setFirstName("Wesley");
        person.setLastName("Pereira da Silva");
        person.setAddress("Marataízes - Espírito Santo - Brasil");
        person.setGender("Male");
    }
}
