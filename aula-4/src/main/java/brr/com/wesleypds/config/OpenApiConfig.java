package brr.com.wesleypds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                        .title("RESTful API with Java 21 and Spring Boot 3.4.2")
                        .version("v1")
                        .description("Some description about your API")
                        .termsOfService("https://www.urlqualquer.com.br")
                        .license(new License()
                                        .name("Apache 2.0")
                                        .url("https://www.urlqualquer.com.br"))
                        );
    }

}
