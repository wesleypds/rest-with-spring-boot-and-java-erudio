package curso.spring.boot.integrationtests.controller;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
import curso.spring.boot.integrationtests.dto.wrapper.WrapperBookDTO;
import curso.spring.boot.integrationtests.testcontainer.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static BookDTO book;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        book = new BookDTO();
    }

    @Test
    @Order(1)
    void testCreate() throws JsonMappingException, JsonProcessingException {
        mockBook();

        specification = new RequestSpecBuilder()
                        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
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
                        .statusCode(200)
                    .extract()
                        .body()
                            .asString();

		book = mapper.readValue(result, BookDTO.class);

        assertNotNull(book.getId());
        assertNotNull(book.getAuthor());
        assertNotNull(book.getLaunchDate());
        assertNotNull(book.getPrice());
        assertNotNull(book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals(LocalDateTime.of(2025, 06, 20, 00, 00, 00), book.getLaunchDate());
        assertEquals(BigDecimal.valueOf(12.99), book.getPrice());
        assertEquals("Java Efetivo", book.getTitle());
    }

    @Test
    @Order(2)
    void testFindById() throws JsonMappingException, JsonProcessingException {

        var result =given(specification)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .pathParam("id", book.getId())
                    .when()
                        .get("/{id}")
                    .then()
                        .statusCode(200)
                    .extract()
                        .body()
                            .asString();

		book = mapper.readValue(result, BookDTO.class);

        assertNotNull(book);
        assertNotNull(book.getId());
        assertNotNull(book.getAuthor());
        assertNotNull(book.getLaunchDate());
        assertNotNull(book.getPrice());
        assertNotNull(book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals(LocalDateTime.of(2025, 06, 20, 00, 00, 00), book.getLaunchDate());
        assertEquals(BigDecimal.valueOf(12.99), book.getPrice());
        assertEquals("Java Efetivo", book.getTitle());
    }

    @Test
    @Order(3)
    void testUpadte() throws JsonMappingException, JsonProcessingException {

        book.setPrice(BigDecimal.valueOf(119.9));

        var result =given(specification)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(book)
                    .when()
                        .put()
                    .then()
                        .statusCode(200)
                    .extract()
                        .body()
                            .asString();

		book = mapper.readValue(result, BookDTO.class);

        assertNotNull(book.getId());
        assertNotNull(book.getAuthor());
        assertNotNull(book.getLaunchDate());
        assertNotNull(book.getPrice());
        assertNotNull(book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals(LocalDateTime.of(2025, 06, 20, 00, 00, 00), book.getLaunchDate());
        assertEquals(BigDecimal.valueOf(119.9), book.getPrice());
        assertEquals("Java Efetivo", book.getTitle());
    }

    @Test
    @Order(4)
    void testDelete() {

        given(specification)
            .pathParam("id", book.getId())
            .when()
                .delete("/{id}")
            .then()
                .statusCode(204);
    }

    @Test
    @Order(5)
    void testFindAll() throws JsonMappingException, JsonProcessingException {

        var result =given(specification)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                        .get()
                    .then()
                        .statusCode(200)
                    .extract()
                        .body()
                            .asString();

        WrapperBookDTO wrapper = mapper.readValue(result, WrapperBookDTO.class);
        List<BookDTO> list = wrapper.getEmbedded().getListObj();

        BookDTO bookZero = list.get(0);

        assertNotNull(bookZero.getId());
        assertNotNull(bookZero.getAuthor());
        assertNotNull(bookZero.getLaunchDate());
        assertNotNull(bookZero.getPrice());
        assertNotNull(bookZero.getTitle());
        assertEquals("Michael C. Feathers", bookZero.getAuthor());
        assertEquals(LocalDate.of(2017, 11, 29), bookZero.getLaunchDate().toLocalDate());
        assertEquals("49.00", bookZero.getPrice().toString());
        assertEquals("Working effectively with legacy code", bookZero.getTitle());

        BookDTO bookSeven = list.get(7);

        assertNotNull(bookSeven.getId());
        assertNotNull(bookSeven.getAuthor());
        assertNotNull(bookSeven.getLaunchDate());
        assertNotNull(bookSeven.getPrice());
        assertNotNull(bookSeven.getTitle());
        assertEquals("Eric Evans", bookSeven.getAuthor());
        assertEquals(LocalDate.of(2017, 11, 29), bookZero.getLaunchDate().toLocalDate());
        assertEquals("92.00", bookSeven.getPrice().toString());
        assertEquals("Domain Driven Design", bookSeven.getTitle());

    }

    private void mockBook() {
        book.setAuthor("Joshua Bloch");
        book.setLaunchDate(LocalDateTime.of(2025, 06, 20, 00, 00, 00));
        book.setPrice(BigDecimal.valueOf(12.99));
        book.setTitle("Java Efetivo");
    }
}
