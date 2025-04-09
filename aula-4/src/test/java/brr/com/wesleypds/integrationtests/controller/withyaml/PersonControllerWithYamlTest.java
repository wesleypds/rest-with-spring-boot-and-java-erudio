package brr.com.wesleypds.integrationtests.controller.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.fasterxml.jackson.databind.JsonMappingException;

import brr.com.wesleypds.configs.TestConfigs;
import brr.com.wesleypds.integrationtests.controller.withyaml.mapper.YAMLMapper;
import brr.com.wesleypds.integrationtests.testcontainers.AbstractIntegrationTest;
import brr.com.wesleypds.integrationtests.vo.AccountCredentialsVO;
import brr.com.wesleypds.integrationtests.vo.PersonVO;
import brr.com.wesleypds.integrationtests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerWithYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;

    private static YAMLMapper mapper;

    private static PersonVO person;

    @BeforeAll
    public static void setup() {
        mapper = new YAMLMapper();
        person = new PersonVO();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonProcessingException {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        var accessToken = given()
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(
                                        EncoderConfig
                                                .encoderConfig()
                                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(user, mapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class, mapper)
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
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(
                                        EncoderConfig
                                                .encoderConfig()
                                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(person, mapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonVO.class, mapper);

        person = content;

        assertNotNull(content);

        assertNotNull(content.getId());
        assertNotNull(content.getFirstName());
        assertNotNull(content.getLastName());
        assertNotNull(content.getAddress());
        assertNotNull(content.getGender());

        assertTrue(content.getId() > 0);

        assertEquals("Nelson", content.getFirstName());
        assertEquals("Piquet", content.getLastName());
        assertEquals("Brasília, DF, BRAZIL", content.getAddress());
        assertEquals("Male", content.getGender());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {
        person.setLastName("Piquet Souto Maior");

        var content = given()
                .spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(
                                        EncoderConfig
                                                .encoderConfig()
                                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(person, mapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonVO.class, mapper);

        person = content;

        assertNotNull(content);

        assertNotNull(content.getId());
        assertNotNull(content.getFirstName());
        assertNotNull(content.getLastName());
        assertNotNull(content.getAddress());
        assertNotNull(content.getGender());

        assertEquals(person.getId(), content.getId());

        assertEquals("Nelson", content.getFirstName());
        assertEquals("Piquet Souto Maior", content.getLastName());
        assertEquals("Brasília, DF, BRAZIL", content.getAddress());
        assertEquals("Male", content.getGender());
    }

    @Test
    @Order(3)
    public void testFindById() throws JsonMappingException, JsonProcessingException {

        var content = given()
                .spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(
                                        EncoderConfig
                                                .encoderConfig()
                                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonVO.class, mapper);

        person = content;

        assertNotNull(content);

        assertNotNull(content.getId());
        assertNotNull(content.getFirstName());
        assertNotNull(content.getLastName());
        assertNotNull(content.getAddress());
        assertNotNull(content.getGender());

        assertTrue(content.getId() > 0);

        assertEquals("Nelson", content.getFirstName());
        assertEquals("Piquet Souto Maior", content.getLastName());
        assertEquals("Brasília, DF, BRAZIL", content.getAddress());
        assertEquals("Male", content.getGender());
    }

    @Test
    @Order(4)
    public void testDelete() throws JsonMappingException, JsonProcessingException {

        given().spec(specification)
                .pathParam("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @SuppressWarnings("unchecked")
    @Test
    @Order(5)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {

        var content = given()
                .spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(
                                        EncoderConfig
                                                .encoderConfig()
                                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<List<PersonVO>>() {}.getType(), mapper);

        List<PersonVO> people = (List<PersonVO>) content;

        PersonVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne);

        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());

        assertEquals(1L, foundPersonOne.getId());

        assertEquals("João", foundPersonOne.getFirstName());
        assertEquals("Silva", foundPersonOne.getLastName());
        assertEquals("Rua das Flores, 123", foundPersonOne.getAddress());
        assertEquals("Masculino", foundPersonOne.getGender());

        PersonVO foundPersonFive = people.get(4);

        assertNotNull(foundPersonFive);

        assertNotNull(foundPersonFive.getId());
        assertNotNull(foundPersonFive.getFirstName());
        assertNotNull(foundPersonFive.getLastName());
        assertNotNull(foundPersonFive.getAddress());
        assertNotNull(foundPersonFive.getGender());

        assertEquals(5L, foundPersonFive.getId());

        assertEquals("Carlos", foundPersonFive.getFirstName());
        assertEquals("Pereira", foundPersonFive.getLastName());
        assertEquals("Rua das Palmeiras, 1213", foundPersonFive.getAddress());
        assertEquals("Masculino", foundPersonFive.getGender());

        PersonVO foundPersonTen = people.get(9);

        assertNotNull(foundPersonTen);

        assertNotNull(foundPersonTen.getId());
        assertNotNull(foundPersonTen.getFirstName());
        assertNotNull(foundPersonTen.getLastName());
        assertNotNull(foundPersonTen.getAddress());
        assertNotNull(foundPersonTen.getGender());

        assertEquals(10L, foundPersonTen.getId());

        assertEquals("Camila", foundPersonTen.getFirstName());
        assertEquals("Ribeiro", foundPersonTen.getLastName());
        assertEquals("Rua dos Lírios, 2223", foundPersonTen.getAddress());
        assertEquals("Feminino", foundPersonTen.getGender());
    }

    @Test
    @Order(6)
    public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

        specification = new RequestSpecBuilder()
                .setBasePath("api/people/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
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
    }

}
