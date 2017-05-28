package io.ohjongsung;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * Created by ohjongsung on 2017-05-08.
 */
@Configuration
@Profile(BlogProfiles.STAGING)
public class StagingDatabaseConfig extends DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl("jdbc:mariadb://13.124.111.73:3306/BLOG_STAGING");
        dataSource.setUsername("ohjongsung");
        dataSource.setPassword("!winners1");
        dataSource.setValidationQuery("SELECT 1");
        logger.debug("staging dataSource");
        configureDataSource(dataSource);
        return dataSource;
    }
}
