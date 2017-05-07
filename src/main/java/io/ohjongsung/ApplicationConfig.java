package io.ohjongsung;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * Created by ohjongsung on 2017-05-06. spring-core-config.xml
 */
@Configuration
@ComponentScan(basePackages = { "io.ohjongsung.blog.service" },
               excludeFilters = @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION))
@Import({PersistenceConfig.class})
public class ApplicationConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }
}
