package io.ohjongsung;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by ohjongsung on 2017-05-06. 디비 컨피그
 */
public abstract class DatabaseConfig {
    protected static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    abstract DataSource dataSource();

    protected void configureDataSource(org.apache.tomcat.jdbc.pool.DataSource dataSource) {
        dataSource.setMaxActive(20);
        dataSource.setMaxIdle(8);
        dataSource.setMinIdle(8);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
    }
}

@Configuration
@Profile(BlogProfiles.DEVELOPMENT)
class DevelopmentDatabaseConfig extends DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:blog");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setValidationQuery("SELECT 1");

        configureDataSource(dataSource);
        logger.debug("datasource is development datasource");
        return dataSource;
    }
}

@Configuration
@Profile(BlogProfiles.STAGING)
class StagingDatabaseConfig extends DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl("jdbc:mariadb://13.124.4.250:3306/BLOG_STAGING");
        dataSource.setUsername("ohjongsung");
        dataSource.setPassword("!winners1");
        dataSource.setValidationQuery("SELECT 1");

        configureDataSource(dataSource);
        logger.debug("datasource is staging datasource");
        return dataSource;
    }
}

@Configuration
@Profile(BlogProfiles.PRODUCTION)
class ProductionDatabaseConfig extends DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl("jdbc:mariadb://13.124.4.250:3306/BLOG");
        dataSource.setUsername("ohjongsung");
        dataSource.setPassword("!winners1");
        dataSource.setValidationQuery("SELECT 1");

        configureDataSource(dataSource);
        logger.debug("datasource is production datasource");
        return dataSource;
    }
}