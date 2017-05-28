package io.ohjongsung;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;

/**
 * Created by ohjongsung on 2017-05-08.
 */
public class DatabaseConfigTest {

    @Test
    public void developmentDataSource() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles(BlogProfiles.DEVELOPMENT);
        ctx.register(DevelopmentDatabaseConfig.class, StagingDatabaseConfig.class, ProductionDatabaseConfig.class);
        ctx.refresh();

        org.apache.tomcat.jdbc.pool.DataSource dataSource = (org.apache.tomcat.jdbc.pool.DataSource)ctx.getBean("dataSource", DataSource.class);
        assertEquals("jdbc:h2:mem:blog", dataSource.getUrl());
    }
}