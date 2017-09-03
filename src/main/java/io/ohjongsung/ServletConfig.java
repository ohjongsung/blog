package io.ohjongsung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.util.UrlPathHelper;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import io.ohjongsung.blog.support.PostCategoryFormatter;
import nz.net.ultraq.thymeleaf.LayoutDialect;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ohjongsung on 2017-05-06. spring-servlet-config.xml
 */
@EnableWebMvc // <mvc:annotation-driven />
@Configuration
@ComponentScan(basePackages = { "io.ohjongsung" },
               includeFilters = @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION))
public class ServletConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new GzipResourceResolver())
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/signin").setViewName("pages/signin");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new PostCategoryFormatter());
    }

    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        // 기본값은 true 이다. 서버 재기동 없이 html 수정이 반영되려면
        // false 로 설정해야 한다.
        resolver.setCacheable(false);
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // Compiled SpringEL should speed up executions
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;

    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(52428800);
        return multipartResolver;
    }

    @Bean(name = { "uih", "viewRenderingHelper" })
    @Scope("request")
    public ViewRenderingHelper viewRenderingHelper() {
        return new ViewRenderingHelper();
    }

    private static class ViewRenderingHelper {

        private final UrlPathHelper urlPathHelper = new UrlPathHelper();

        private HttpServletRequest request;

        @Autowired
        public void setRequest(HttpServletRequest request) {
            this.request = request;
        }

        public String blogClass(String active, String current) {
            if (active.equals(current)) {
                return "w3-leftbar w3-border-green";
            } else {
                return "w3-leftbar";
            }
        }

        public String path() {
            return urlPathHelper.getPathWithinApplication(request);
        }

    }
}
