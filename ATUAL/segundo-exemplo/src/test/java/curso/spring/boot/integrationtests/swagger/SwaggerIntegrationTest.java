package curso.spring.boot.integrationtests.swagger;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.*;

import curso.spring.boot.config.TestConfigs;
import curso.spring.boot.integrationtests.testcontainer.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	void shouldDisplaySwaggerUIPage() {
		var result =given()
						.basePath("/swagger-ui/index.html")
							.port(TestConfigs.SERVER_PORT)
						.when()
							.get()
						.then()
							.statusCode(200)
						.extract()
							.body()
								.asString();

		assertTrue(result.contains("Swagger UI"));
	}

}
