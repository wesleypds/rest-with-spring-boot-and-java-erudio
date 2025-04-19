package brr.com.wesleypds.integrationtests.testcontainers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        static PostgreSQLContainer<?> pgvector = new PostgreSQLContainer<>("pgvector/pgvector:pg16");

        private static void startContainers() {
            Startables.deepStart(Stream.of(pgvector)).join();
        }

        private static Map<String, String> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", pgvector.getJdbcUrl(),
                    "spring.datasource.username", pgvector.getUsername(),
                    "spring.datasource.password", pgvector.getPassword());
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment configurableEnvironment = applicationContext.getEnvironment();
            MapPropertySource testContainers = new MapPropertySource("testcontainers",
                    (Map) createConnectionConfiguration());
            configurableEnvironment.getPropertySources().addFirst(testContainers);
        }
    }

}
