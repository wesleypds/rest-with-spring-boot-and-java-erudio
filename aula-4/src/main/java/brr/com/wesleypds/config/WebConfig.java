package brr.com.wesleypds.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import brr.com.wesleypds.serialization.converter.YamlJackson2HttpMesageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // Via QUERY PARAM
        // configurer.favorParameter(true)
        // .parameterName("mediaType").ignoreAcceptHeader(true)
        // .useRegisteredExtensionsOnly(false)
        // .defaultContentType(MediaType.APPLICATION_JSON)
        // .mediaType(".json", MediaType.APPLICATION_JSON)
        // .mediaType(".xml", MediaType.APPLICATION_XML);

        // VIA HEADER PARAM
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                    .mediaType("json", MediaType.APPLICATION_JSON)
                    .mediaType("xml", MediaType.APPLICATION_XML)
                    .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJackson2HttpMesageConverter());
    }

}
