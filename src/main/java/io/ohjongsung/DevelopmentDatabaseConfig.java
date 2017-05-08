package io.ohjongsung;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Created by ohjongsung on 2017-05-08.
 */
@Configuration
@Profile(BlogProfiles.DEVELOPMENT)
public class DevelopmentDatabaseConfig extends DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:blog");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setValidationQuery("SELECT 1");
        logger.debug("development dataSource");
        configureDataSource(dataSource);
        return dataSource;
    }
}
