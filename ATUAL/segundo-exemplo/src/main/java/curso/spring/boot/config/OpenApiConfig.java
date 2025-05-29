package curso.spring.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                    .info(new Info()
                                .title("REST API's RESTful from 0 with Java, Spring Boot, Kubernetes and Docker")
                                .version("v1")
                    );
    }

}
