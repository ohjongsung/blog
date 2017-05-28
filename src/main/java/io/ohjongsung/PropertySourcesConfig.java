package io.ohjongsung;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by ohjongsung on 2017-05-21.
 */
@Configuration
public class PropertySourcesConfig {

    private static final Resource[] DEVELOPMENT_PROPERTIES = new ClassPathResource[]{
            new ClassPathResource("development.properties"),
    };
    private static final Resource[] STAGING_PROPERTIES = new ClassPathResource[]{
            new ClassPathResource("staging.properties"),
    };
    private static final Resource[] PRODUCTION_PROPERTIES = new ClassPathResource[]{
            new ClassPathResource("production.properties"),
    };

    @Profile(BlogProfiles.DEVELOPMENT)
    public static class DevConfig {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
            pspc.setLocations(DEVELOPMENT_PROPERTIES);
            return pspc;
        }
    }

    @Profile(BlogProfiles.STAGING)
    public static class TestConfig {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
            pspc.setLocations(STAGING_PROPERTIES);
            return pspc;
        }
    }

    @Profile(BlogProfiles.PRODUCTION)
    public static class ProdConfig {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
            pspc.setLocations(PRODUCTION_PROPERTIES);
            return pspc;
        }
    }
}
