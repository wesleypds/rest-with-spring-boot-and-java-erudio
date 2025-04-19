package brr.com.wesleypds.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import brr.com.wesleypds.integrationtests.vo.BookVO;
import brr.com.wesleypds.integrationtests.vo.TokenVO;
import brr.com.wesleypds.integrationtests.vo.wrappers.WrapperBookVOJson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerWithJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;

    private static ObjectMapper mapper;

    private static BookVO book;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        book = new BookVO();
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
                .setBasePath("api/books/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonMappingException, JsonProcessingException {
        mockBook();

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        BookVO bookPersisted = mapper.readValue(content, BookVO.class);

        assertNotNull(bookPersisted);
        assertNotNull(bookPersisted.getId());
        assertNotNull(bookPersisted.getAuthor());
        assertNotNull(bookPersisted.getLaunchDate());
        assertNotNull(bookPersisted.getPrice());
        assertNotNull(bookPersisted.getTitle());

        assertTrue(bookPersisted.getId() > 0);
        assertEquals(book.getAuthor(), bookPersisted.getAuthor());
        assertEquals(book.getLaunchDate(), bookPersisted.getLaunchDate());
        assertEquals(book.getPrice(), bookPersisted.getPrice());
        assertEquals(book.getTitle(), bookPersisted.getTitle());

        book = bookPersisted;
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {
        book.setTitle("A jornada do leitor - Como se tornar um leitor estratégico");

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(book)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        BookVO bookPersisted = mapper.readValue(content, BookVO.class);

        assertNotNull(bookPersisted);
        assertNotNull(bookPersisted.getId());
        assertNotNull(bookPersisted.getAuthor());
        assertNotNull(bookPersisted.getLaunchDate());
        assertNotNull(bookPersisted.getPrice());
        assertNotNull(bookPersisted.getTitle());

        assertEquals(book.getId(), bookPersisted.getId());
        assertEquals(book.getAuthor(), bookPersisted.getAuthor());
        assertEquals(book.getLaunchDate(), bookPersisted.getLaunchDate());
        assertEquals(book.getPrice(), bookPersisted.getPrice());
        assertEquals(book.getTitle(), bookPersisted.getTitle());

        book = bookPersisted;
    }

    @Test
    @Order(3)
    public void testFindById() throws JsonMappingException, JsonProcessingException {

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", book.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        BookVO bookPersisted = mapper.readValue(content, BookVO.class);

        assertNotNull(bookPersisted);
        assertNotNull(bookPersisted.getId());
        assertNotNull(bookPersisted.getAuthor());
        assertNotNull(bookPersisted.getLaunchDate());
        assertNotNull(bookPersisted.getPrice());
        assertNotNull(bookPersisted.getTitle());

        assertEquals(book.getId(), bookPersisted.getId());
        assertEquals(book.getAuthor(), bookPersisted.getAuthor());
        assertEquals(book.getLaunchDate(), bookPersisted.getLaunchDate());
        assertEquals(book.getPrice(), bookPersisted.getPrice());
        assertEquals(book.getTitle(), bookPersisted.getTitle());

        book = bookPersisted;
    }

    @Test
    @Order(4)
    public void testDelete() throws JsonMappingException, JsonProcessingException {

        given().spec(specification)
                .pathParam("id", book.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    @Order(5)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {

        var content = given()
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

        WrapperBookVOJson wrapperBookVO = mapper.readValue(content, WrapperBookVOJson.class);

        List<BookVO> books = wrapperBookVO.getEmbedded().getBooks();

        BookVO foundBookOne = books.get(0);

        assertNotNull(foundBookOne);
        assertNotNull(foundBookOne.getId());
        assertNotNull(foundBookOne.getAuthor());
        assertNotNull(foundBookOne.getLaunchDate());
        assertNotNull(foundBookOne.getPrice());
        assertNotNull(foundBookOne.getTitle());
        
        assertEquals(15L, foundBookOne.getId());
        assertEquals("Aguinaldo Aragon Fernandes e Vladimir Ferraz de Abreu", foundBookOne.getAuthor());
        assertEquals("2017-11-07 15:09:01.674", DATE_FORMAT.format(foundBookOne.getLaunchDate()));
        assertEquals(54.0, foundBookOne.getPrice());
        assertEquals("Implantando a governança de TI", foundBookOne.getTitle());

        BookVO foundBookFive = books.get(4);

        assertNotNull(foundBookFive);
        assertNotNull(foundBookFive.getId());
        assertNotNull(foundBookFive.getAuthor());
        assertNotNull(foundBookFive.getLaunchDate());
        assertNotNull(foundBookFive.getPrice());
        assertNotNull(foundBookFive.getTitle());

        assertEquals(7L, foundBookFive.getId());
        assertEquals("Eric Freeman, Elisabeth Freeman, Kathy Sierra, Bert Bates", foundBookFive.getAuthor());
        assertEquals("2017-11-07 15:09:01.674", DATE_FORMAT.format(foundBookFive.getLaunchDate()));
        assertEquals(110.0, foundBookFive.getPrice());
        assertEquals("Head First Design Patterns", foundBookFive.getTitle());

        BookVO foundBookTen = books.get(9);

        assertNotNull(foundBookTen);
        assertNotNull(foundBookTen.getId());
        assertNotNull(foundBookTen.getAuthor());
        assertNotNull(foundBookTen.getLaunchDate());
        assertNotNull(foundBookTen.getPrice());
        assertNotNull(foundBookTen.getTitle());

        assertEquals(13L, foundBookTen.getId());
        assertEquals("Richard Hunter e George Westerman", foundBookTen.getAuthor());
        assertEquals("2017-11-07 15:09:01.674", DATE_FORMAT.format(foundBookTen.getLaunchDate()));
        assertEquals(95.0, foundBookTen.getPrice());
        assertEquals("O verdadeiro valor de TI", foundBookTen.getTitle());
    }

    @Test
    @Order(6)
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
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/books/v1/15\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/books/v1/9\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/books/v1/4\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/books/v1/8\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/books/v1/7\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/books/v1/14\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/books/v1/6\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/books/v1/1\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/books/v1/2\"}}"));
        assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/books/v1/13\"}}"));
        assertTrue(content.contains("\"href\":\"http://localhost:8888/api/books/v1?direction=asc&page=0&size=10&sort=author,asc\""));
        assertTrue(content.contains("\"href\":\"http://localhost:8888/api/books/v1?page=0&size=10&direction=asc\""));
        assertTrue(content.contains("\"href\":\"http://localhost:8888/api/books/v1?direction=asc&page=1&size=10&sort=author,asc\""));
        assertTrue(content.contains("\"href\":\"http://localhost:8888/api/books/v1?direction=asc&page=1&size=10&sort=author,asc\""));
        assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":15,\"totalPages\":2,\"number\":0}"));
        
    }

    @Test
    @Order(7)
    public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

        specification = new RequestSpecBuilder()
                .setBasePath("api/books/v1")
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

    @SuppressWarnings("deprecation")
    private void mockBook() {
        book.setAuthor("Daniel Lopez");
        book.setLaunchDate(new Date(2025, 4, 9));
        book.setPrice(45.9);
        book.setTitle("A jornada do leitor");
    }

}
