package io.ohjongsung.blog.author.entity;

import org.junit.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Created by ohjongsung on 2017-05-07. 작성자 프로필 테스트
 */
public class AuthorProfileTest {

    @Test
    public void fullNameUsesNameIfAvailable() {
        AuthorProfile nick = new AuthorProfile();
        nick.setUsername("ohjongsung");
        nick.setName("JongSung OH");
        assertThat(nick.getFullName(), equalTo("JongSung OH"));
    }

    @Test
    public void fullNameFallsBackToUsername() {
        AuthorProfile nick = new AuthorProfile();
        nick.setUsername("ohjongsung");
        nick.setName(null);
        assertThat(nick.getFullName(), equalTo("ohjongsung"));
    }

    @Test
    public void githubLink() {
        AuthorProfile nick = new AuthorProfile();
        nick.setGithubUsername("ohjongsung");
        assertThat(nick.getGithubLink().getHref(), equalTo("https://github.com/ohjongsung"));
        assertThat(nick.getGithubLink().getText(), equalTo("github.com/ohjongsung"));
    }

    @Test
    public void emptyGithubLink() {
        AuthorProfile nick = new AuthorProfile();
        assertThat(nick.getGithubLink(), is(nullValue()));
    }

    @Test
    public void nullGithubLink() {
        AuthorProfile nick = new AuthorProfile();
        nick.setGithubUsername("");
        assertThat(nick.getGithubLink(), is(nullValue()));
    }

    @Test
    public void isNotHiddenByDefault() {
        AuthorProfile nick = new AuthorProfile();
        assertThat(nick.isHidden(), is(false));
    }
}