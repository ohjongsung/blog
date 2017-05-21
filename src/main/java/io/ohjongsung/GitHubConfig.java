package io.ohjongsung;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.github.api.GitHub;
import org.springframework.social.github.api.impl.GitHubTemplate;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.util.StringUtils;

/**
 * Created by ohjongsung on 2017-05-13. GutHub API 사용을 위한 인증 컨피그
 */
@Configuration
public class GitHubConfig {

    public static final Logger logger = LoggerFactory.getLogger(GitHubConfig.class);

    @Value("${github.client.id}")
    private String githubClientId;

    @Value("${github.client.secret}")
    private String githubClientSecret;

    @Value("${github.access.token}")
    private String githubAccessToken;

    @Bean
    public GitHubConnectionFactory gitHubConnectionFactory() {
        GitHubConnectionFactory factory = new GitHubConnectionFactory(githubClientId, githubClientSecret);
        factory.setScope("user");
        return factory;
    }

    @Bean
    public GitHub gitHubTemplate() {
        if (StringUtils.isEmpty(githubAccessToken)) {
            return new GuideGitHubTemplate();
        }
        return new GuideGitHubTemplate(githubAccessToken);
    }

    private static class GuideGitHubTemplate extends GitHubTemplate {

        private GuideGitHubTemplate() {
            super();
            logger.warn("GitHub API access will be rate-limited at 60 req/hour");
        }

        private GuideGitHubTemplate(String githubAccessToken) {
            super(githubAccessToken);
        }

        @Override
        protected List<HttpMessageConverter<?>> getMessageConverters() {
            List<HttpMessageConverter<?>> converters = new ArrayList<>();
            converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
            return converters;
        }
    }
}
