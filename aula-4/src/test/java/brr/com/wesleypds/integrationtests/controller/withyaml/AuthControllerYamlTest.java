package brr.com.wesleypds.integrationtests.controller.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import brr.com.wesleypds.configs.TestConfigs;
import brr.com.wesleypds.integrationtests.controller.withyaml.mapper.YAMLMapper;
import brr.com.wesleypds.integrationtests.testcontainers.AbstractIntegrationTest;
import brr.com.wesleypds.integrationtests.vo.AccountCredentialsVO;
import brr.com.wesleypds.integrationtests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerYamlTest extends AbstractIntegrationTest {

    private static YAMLMapper yamlMapper;
    private static TokenVO tokenVO;

    @BeforeAll
    public static void setUp() {
        yamlMapper = new YAMLMapper();
    }

    @Test
    @Order(1)
    public void signin() throws JsonProcessingException {

        RequestSpecification specification = new RequestSpecBuilder()
                                                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                                                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                                            .build();
            
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        tokenVO = given().spec(specification)
                    .config(
                        RestAssuredConfig
                            .config()
                                .encoderConfig(
                                    EncoderConfig
                                        .encoderConfig()
                                            .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
                    ))
                    .accept(TestConfigs.CONTENT_TYPE_YML)
                    .basePath("/auth/signin")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(TestConfigs.CONTENT_TYPE_YML)
                        .body(user, yamlMapper)
                    .when()
                        .post()
                    .then()
                        .statusCode(200)
                    .extract()
                        .body()
                            .as(TokenVO.class, yamlMapper);

        assertNotNull(tokenVO.getAccessToken());
        assertNotNull(tokenVO.getRefreshToken());
    }

    @Test
    @Order(2)
    public void refresh() {
        RequestSpecification specification = new RequestSpecBuilder()
                                                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                                                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                                            .build();

        var newTokenVO = given().spec(specification)
                        .config(
                            RestAssuredConfig
                                .config()
                                    .encoderConfig(
                                        EncoderConfig
                                            .encoderConfig()
                                                .encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
                        ))
                        .accept(TestConfigs.CONTENT_TYPE_YML)
                        .basePath("/auth/refresh-token")
                        .port(TestConfigs.SERVER_PORT)
                        .contentType(TestConfigs.CONTENT_TYPE_YML)
                            .param("username", tokenVO.getUserName())
                            .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
                        .when()
                            .put()
                        .then()
                            .statusCode(200)
                        .extract()
                            .body()
                                .as(TokenVO.class, yamlMapper);

        assertNotNull(newTokenVO.getAccessToken());
        assertNotNull(newTokenVO.getRefreshToken());
    }

}
