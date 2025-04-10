package brr.com.wesleypds.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import brr.com.wesleypds.configs.TestConfigs;
import brr.com.wesleypds.integrationtests.testcontainers.AbstractIntegrationTest;
import brr.com.wesleypds.integrationtests.vo.AccountCredentialsVO;
import brr.com.wesleypds.integrationtests.vo.PersonVO;
import brr.com.wesleypds.integrationtests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerWithJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;

    private static ObjectMapper mapper;

    private static PersonVO person;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        person = new PersonVO();
    }

    @Test
    @Order(0)
    public void authorization() {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        var accessToken = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class)
                .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("api/people/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonVO personPersisted = mapper.readValue(content, PersonVO.class);

        assertNotNull(personPersisted);
        assertNotNull(personPersisted.getId());
        assertNotNull(personPersisted.getFirstName());
        assertNotNull(personPersisted.getLastName());
        assertNotNull(personPersisted.getAddress());
        assertNotNull(personPersisted.getGender());
        assertNotNull(personPersisted.getEnabled());

        assertTrue(personPersisted.getId() > 0);
        assertEquals(person.getFirstName(), personPersisted.getFirstName());
        assertEquals(person.getLastName(), personPersisted.getLastName());
        assertEquals(person.getAddress(), personPersisted.getAddress());
        assertEquals(person.getGender(), personPersisted.getGender());
        assertEquals(person.getEnabled(), personPersisted.getEnabled());

        person = personPersisted;
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {
        person.setLastName("Piquet Souto Maior");

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonVO personPersisted = mapper.readValue(content, PersonVO.class);

        assertNotNull(personPersisted);
        assertNotNull(personPersisted.getId());
        assertNotNull(personPersisted.getFirstName());
        assertNotNull(personPersisted.getLastName());
        assertNotNull(personPersisted.getAddress());
        assertNotNull(personPersisted.getGender());
        assertNotNull(personPersisted.getEnabled());

        assertEquals(person.getId(), personPersisted.getId());
        assertEquals(person.getFirstName(), personPersisted.getFirstName());
        assertEquals(person.getLastName(), personPersisted.getLastName());
        assertEquals(person.getAddress(), personPersisted.getAddress());
        assertEquals(person.getGender(), personPersisted.getGender());
        assertEquals(person.getEnabled(), personPersisted.getEnabled());

        person = personPersisted;
    }

    @Test
    @Order(3)
    public void testFindById() throws JsonMappingException, JsonProcessingException {

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonVO personPersisted = mapper.readValue(content, PersonVO.class);

        assertNotNull(personPersisted);
        assertNotNull(personPersisted.getId());
        assertNotNull(personPersisted.getFirstName());
        assertNotNull(personPersisted.getLastName());
        assertNotNull(personPersisted.getAddress());
        assertNotNull(personPersisted.getGender());
        assertNotNull(personPersisted.getEnabled());

        assertEquals(person.getId(), personPersisted.getId());
        assertEquals(person.getFirstName(), personPersisted.getFirstName());
        assertEquals(person.getLastName(), personPersisted.getLastName());
        assertEquals(person.getAddress(), personPersisted.getAddress());
        assertEquals(person.getGender(), personPersisted.getGender());
        assertEquals(person.getEnabled(), personPersisted.getEnabled());

        person = personPersisted;
    }

    @Test
    @Order(4)
    public void testDisablePerson() throws JsonMappingException, JsonProcessingException {

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", person.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonVO personPersisted = mapper.readValue(content, PersonVO.class);

        assertNotNull(personPersisted);
        assertNotNull(personPersisted.getId());
        assertNotNull(personPersisted.getFirstName());
        assertNotNull(personPersisted.getLastName());
        assertNotNull(personPersisted.getAddress());
        assertNotNull(personPersisted.getGender());
        assertNotNull(personPersisted.getEnabled());

        assertEquals(person.getId(), personPersisted.getId());
        assertEquals(person.getFirstName(), personPersisted.getFirstName());
        assertEquals(person.getLastName(), personPersisted.getLastName());
        assertEquals(person.getAddress(), personPersisted.getAddress());
        assertEquals(person.getGender(), personPersisted.getGender());
        assertNotEquals(person.getEnabled(), personPersisted.getEnabled());

        person = personPersisted;
    }

    @Test
    @Order(5)
    public void testDelete() throws JsonMappingException, JsonProcessingException {

        given().spec(specification)
                .pathParam("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    @Order(6)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        List<PersonVO> people = mapper.readValue(content, new TypeReference<List<PersonVO>>() {
        });

        PersonVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne);
        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());
        assertNotNull(foundPersonOne.getEnabled());

        assertEquals(1L, foundPersonOne.getId());
        assertEquals("João", foundPersonOne.getFirstName());
        assertEquals("Silva", foundPersonOne.getLastName());
        assertEquals("Rua das Flores, 123", foundPersonOne.getAddress());
        assertEquals("Masculino", foundPersonOne.getGender());
        assertEquals(true, foundPersonOne.getEnabled());

        PersonVO foundPersonFive = people.get(4);

        assertNotNull(foundPersonFive);
        assertNotNull(foundPersonFive.getId());
        assertNotNull(foundPersonFive.getFirstName());
        assertNotNull(foundPersonFive.getLastName());
        assertNotNull(foundPersonFive.getAddress());
        assertNotNull(foundPersonFive.getGender());
        assertNotNull(foundPersonFive.getEnabled());

        assertEquals(5L, foundPersonFive.getId());
        assertEquals("Carlos", foundPersonFive.getFirstName());
        assertEquals("Pereira", foundPersonFive.getLastName());
        assertEquals("Rua das Palmeiras, 1213", foundPersonFive.getAddress());
        assertEquals("Masculino", foundPersonFive.getGender());
        assertEquals(true, foundPersonFive.getEnabled());

        PersonVO foundPersonTen = people.get(9);

        assertNotNull(foundPersonTen);
        assertNotNull(foundPersonTen.getId());
        assertNotNull(foundPersonTen.getFirstName());
        assertNotNull(foundPersonTen.getLastName());
        assertNotNull(foundPersonTen.getAddress());
        assertNotNull(foundPersonTen.getGender());
        assertNotNull(foundPersonTen.getEnabled());

        assertEquals(10L, foundPersonTen.getId());
        assertEquals("Camila", foundPersonTen.getFirstName());
        assertEquals("Ribeiro", foundPersonTen.getLastName());
        assertEquals("Rua dos Lírios, 2223", foundPersonTen.getAddress());
        assertEquals("Feminino", foundPersonTen.getGender());
        assertEquals(true, foundPersonTen.getEnabled());
    }

    @Test
    @Order(7)
    public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

        specification = new RequestSpecBuilder()
                .setBasePath("api/people/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract()
                .body()
                .asString();

    }

    private void mockPerson() {
        person.setFirstName("Nelson");
        person.setLastName("Piquet");
        person.setAddress("Brasília, DF, BRAZIL");
        person.setGender("Male");
        person.setEnabled(true);
    }

}
