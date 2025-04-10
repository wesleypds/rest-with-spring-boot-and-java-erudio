package brr.com.wesleypds.integrationtests.controller.cors.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
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
public class PersonControllerJsonTest extends AbstractIntegrationTest {

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
                .header(TestConfigs.HEADER_PARAM_ORIGN, TestConfigs.ORIGIN_ERUDIO)
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
    public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGN, TestConfigs.ORIGIN_GOOGLE)
                    .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(403)
                .extract()
                    .body()
                        .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    public void testFindById() throws JsonMappingException, JsonProcessingException {

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGN, TestConfigs.ORIGIN_ERUDIO)
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
    public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGN, TestConfigs.ORIGIN_GOOGLE)
                    .pathParam("id", person.getId())
                .when()
                    .get("{id}")
                .then()
                    .statusCode(403)
                .extract()
                    .body()
                        .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    private void mockPerson() {
        person.setFirstName("Nelson");
        person.setLastName("Piquet");
        person.setAddress("Brasília, DF, BRAZIL");
        person.setGender("Male");
        person.setEnabled(true);
    }

}
