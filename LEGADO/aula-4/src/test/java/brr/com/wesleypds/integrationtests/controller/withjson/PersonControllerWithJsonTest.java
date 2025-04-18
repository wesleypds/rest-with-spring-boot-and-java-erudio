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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import brr.com.wesleypds.configs.TestConfigs;
import brr.com.wesleypds.integrationtests.testcontainers.AbstractIntegrationTest;
import brr.com.wesleypds.integrationtests.vo.AccountCredentialsVO;
import brr.com.wesleypds.integrationtests.vo.PersonVO;
import brr.com.wesleypds.integrationtests.vo.TokenVO;
import brr.com.wesleypds.integrationtests.vo.wrappers.WrapperPersonVOJson;
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
                .queryParam("page", 3)
                .queryParam("size", 10)
                .queryParam("direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        WrapperPersonVOJson wrapperPersoVO = mapper.readValue(content, WrapperPersonVOJson.class);

        List<PersonVO> people = wrapperPersoVO.getEmbedded().getPeople();

        PersonVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne);
        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());
        assertNotNull(foundPersonOne.getEnabled());

        assertEquals(40L, foundPersonOne.getId());
        assertEquals("Diego", foundPersonOne.getFirstName());
        assertEquals("Mendes", foundPersonOne.getLastName());
        assertEquals("Feira Nicolas Souza, 79", foundPersonOne.getAddress());
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

        assertEquals(81L, foundPersonFive.getId());
        assertEquals("Eduardo", foundPersonFive.getFirstName());
        assertEquals("Ribeiro", foundPersonFive.getLastName());
        assertEquals("Quadra Cardoso", foundPersonFive.getAddress());
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

        assertEquals(97L, foundPersonTen.getId());
        assertEquals("Enzo Gabriel", foundPersonTen.getFirstName());
        assertEquals("Moreira", foundPersonTen.getLastName());
        assertEquals("Sítio de Ferreira, 180", foundPersonTen.getAddress());
        assertEquals("Masculino", foundPersonTen.getGender());
        assertEquals(true, foundPersonTen.getEnabled());
    }

    @Test
    @Order(7)
    public void testFindPeopleByName() throws JsonMappingException, JsonProcessingException {

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .queryParam("direction", "asc")
                .when()
                .get("/find-people-by-name/car")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        WrapperPersonVOJson wrapperPersoVO = mapper.readValue(content, WrapperPersonVOJson.class);

        List<PersonVO> people = wrapperPersoVO.getEmbedded().getPeople();

        PersonVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne);
        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());
        assertNotNull(foundPersonOne.getEnabled());

        assertEquals(9L, foundPersonOne.getId());
        assertEquals("Ricardo", foundPersonOne.getFirstName());
        assertEquals("Gomes", foundPersonOne.getLastName());
        assertEquals("Avenida das Rosas, 2021", foundPersonOne.getAddress());
        assertEquals("Masculino", foundPersonOne.getGender());
        assertEquals(true, foundPersonOne.getEnabled());

    }

    @Test
    @Order(8)
    public void testFindAllContainsLinks() throws JsonMappingException, JsonProcessingException {

        var unthreatedContent = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .queryParam("direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        var content = unthreatedContent.replace("\n", "").replace("\r", "");        
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/people/v1/108\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/people/v1/47\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/people/v1/4\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/people/v1/57\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/people/v1/68\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/people/v1/75\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/people/v1/26\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/people/v1/38\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/people/v1/19\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/people/v1/89\"}}"));
        assertTrue(content.contains("\"href\":\"http://localhost:8888/api/people/v1?direction=asc&page=0&size=10&sort=firstName,asc\""));
        assertTrue(content.contains("\"href\":\"http://localhost:8888/api/people/v1?page=0&size=10&direction=asc\""));
        assertTrue(content.contains("\"href\":\"http://localhost:8888/api/people/v1?direction=asc&page=1&size=10&sort=firstName,asc\""));
        assertTrue(content.contains("\"href\":\"http://localhost:8888/api/people/v1?direction=asc&page=10&size=10&sort=firstName,asc\""));
        assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":110,\"totalPages\":11,\"number\":0}"));
        
    }

    @Test
    @Order(9)
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
