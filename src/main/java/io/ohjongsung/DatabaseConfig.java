package io.ohjongsung;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

/**
 * Created by ohjongsung on 2017-05-06. 디비 컨피그
 */
public abstract class DatabaseConfig {
    protected static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    protected abstract DataSource dataSource();

    protected void configureDataSource(org.apache.tomcat.jdbc.pool.DataSource dataSource) {
        dataSource.setMaxActive(20);
        dataSource.setMaxIdle(8);
        dataSource.setMinIdle(8);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
    }
}