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
        ctx.getEnvironment().setActiveProfiles("development");
        ctx.register(DevelopmentDatabaseConfig.class, StagingDatabaseConfig.class, ProductionDatabaseConfig.class);
        ctx.refresh();

        org.apache.tomcat.jdbc.pool.DataSource dataSource = (org.apache.tomcat.jdbc.pool.DataSource)ctx.getBean("dataSource", DataSource.class);
        assertEquals("jdbc:h2:mem:blog", dataSource.getUrl());
    }

    @Test
    public void stagingDataSource() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("staging");
        ctx.register(DevelopmentDatabaseConfig.class, StagingDatabaseConfig.class, ProductionDatabaseConfig.class);
        ctx.refresh();

        org.apache.tomcat.jdbc.pool.DataSource dataSource = (org.apache.tomcat.jdbc.pool.DataSource)ctx.getBean("dataSource", DataSource.class);
        assertEquals("jdbc:mariadb://13.124.111.73:3306/BLOG_STAGING", dataSource.getUrl());
    }

    @Test
    public void productionDataSource() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("production");
        ctx.register(DevelopmentDatabaseConfig.class, StagingDatabaseConfig.class, ProductionDatabaseConfig.class);
        ctx.refresh();

        org.apache.tomcat.jdbc.pool.DataSource dataSource = (org.apache.tomcat.jdbc.pool.DataSource)ctx.getBean("dataSource", DataSource.class);
        assertEquals("jdbc:mariadb://13.124.111.73:3306/BLOG", dataSource.getUrl());
    }

}