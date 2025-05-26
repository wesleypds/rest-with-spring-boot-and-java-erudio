package curso.spring.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        /*
         * Configuração de Content Negotiation usando Query Param
         */
        // configurer.favorParameter(true)
        //             .parameterName("mediaType")
        //             .ignoreAcceptHeader(true)
        //             .useRegisteredExtensionsOnly(false)
        //             .defaultContentType(MediaType.APPLICATION_JSON)
        //                 .mediaType("json", MediaType.APPLICATION_JSON)
        //                 .mediaType("xml", MediaType.APPLICATION_XML);

        /*
         * Configuração de Content Negotiation usando Header Param
         */
        configurer.favorParameter(false)
                    .ignoreAcceptHeader(false)
                    .useRegisteredExtensionsOnly(false)
                    .defaultContentType(MediaType.APPLICATION_JSON)
                        .mediaType("json", MediaType.APPLICATION_JSON)
                        .mediaType("xml", MediaType.APPLICATION_XML)
                        .mediaType("yaml", MediaType.APPLICATION_YAML);
    }

}
