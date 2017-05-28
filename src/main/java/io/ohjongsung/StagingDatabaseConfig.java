package io.ohjongsung;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

/**
 * Created by ohjongsung on 2017-05-08.
 */
@Configuration
@Profile(BlogProfiles.STAGING)
public class StagingDatabaseConfig {
    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        return lookup.getDataSource("jdbc/BlogDB");
    }
}
