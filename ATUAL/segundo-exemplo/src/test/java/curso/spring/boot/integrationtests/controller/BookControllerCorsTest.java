package curso.spring.boot.integrationtests.controller;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
import curso.spring.boot.integrationtests.dto.BookDTO;
import curso.spring.boot.integrationtests.testcontainer.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerCorsTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static BookDTO book;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        book = new BookDTO();
    }

    @Test
    @Order(1)
    void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
        mockBook();

        specification = new RequestSpecBuilder()
                        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_GOOGLE)
                        .setBasePath("/api/books/v1")
                        .setPort(TestConfigs.SERVER_PORT)
                            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                        .build();

        var result =given(specification)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(book)
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
    @Order(2)
    void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {

        var result =given(specification)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .pathParam("id", book.getId())
                    .when()
                        .get("/{id}")
                    .then()
                        .statusCode(403)
                    .extract()
                        .body()
                            .asString();

		assertEquals("Invalid CORS request", result);
    }

    @Test
    @Order(3)
    void testUpadteWithWrongOrigin() throws JsonMappingException, JsonProcessingException {

        var result =given(specification)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(book)
                    .when()
                        .put()
                    .then()
                        .statusCode(403)
                    .extract()
                        .body()
                            .asString();

        assertEquals("Invalid CORS request", result);
    }

    @Test
    @Order(4)
    void testDeleteWithWrongOrigin() {

        var result =given(specification)
                    .pathParam("id", book.getId())
                    .when()
                        .delete("/{id}")
                    .then()
                        .statusCode(403)
                    .extract()
                        .body()
                            .asString();

        assertEquals("Invalid CORS request", result);
            
    }

    @Test
    @Order(5)
    void testFindAllWithWrongOrgin() {

        var result =given(specification)
                    .when()
                        .get()
                    .then()
                        .statusCode(403)
                    .extract()
                        .body()
                            .asString();

        assertEquals("Invalid CORS request", result);

    }

    private void mockBook() {
        book.setId(1L);
        book.setAuthor("Joshua Bloch");
        book.setLaunchDate(LocalDateTime.of(2025, 06, 20, 00, 00, 00));
        book.setPrice(BigDecimal.valueOf(12.99));
        book.setTitle("Java Efetivo");
    }
}
