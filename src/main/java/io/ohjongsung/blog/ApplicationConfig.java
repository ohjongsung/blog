package io.ohjongsung.blog;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

/**
 * Created by ohjongsung on 2017-05-06. spring-core-config.xml
 */
@Configuration
@ComponentScan(basePackages = { "io.ohjongsung.blog.service" },
               excludeFilters = @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION))
@Import({DevelopmentDatabaseConfig.class, StagingDatabaseConfig.class, ProductionDatabaseConfig.class, PersistenceConfig.class})
public class ApplicationConfig {
}
