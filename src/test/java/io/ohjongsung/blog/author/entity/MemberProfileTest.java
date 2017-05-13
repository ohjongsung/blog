package io.ohjongsung.blog.author.entity;

import org.junit.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Created by ohjongsung on 2017-05-07. 작성자 프로필 테스트
 */
public class MemberProfileTest {

    @Test
    public void fullNameUsesNameIfAvailable() {
        MemberProfile nick = new MemberProfile();
        nick.setUsername("ohjongsung");
        nick.setName("JongSung OH");
        assertThat(nick.getFullName(), equalTo("JongSung OH"));
    }

    @Test
    public void fullNameFallsBackToUsername() {
        MemberProfile nick = new MemberProfile();
        nick.setUsername("ohjongsung");
        nick.setName(null);
        assertThat(nick.getFullName(), equalTo("ohjongsung"));
    }

    @Test
    public void githubLink() {
        MemberProfile nick = new MemberProfile();
        nick.setGithubUsername("ohjongsung");
        assertThat(nick.getGithubLink().getHref(), equalTo("https://github.com/ohjongsung"));
        assertThat(nick.getGithubLink().getText(), equalTo("github.com/ohjongsung"));
    }

    @Test
    public void emptyGithubLink() {
        MemberProfile nick = new MemberProfile();
        assertThat(nick.getGithubLink(), is(nullValue()));
    }

    @Test
    public void nullGithubLink() {
        MemberProfile nick = new MemberProfile();
        nick.setGithubUsername("");
        assertThat(nick.getGithubLink(), is(nullValue()));
    }

    @Test
    public void isNotHiddenByDefault() {
        MemberProfile nick = new MemberProfile();
        assertThat(nick.isHidden(), is(false));
    }
}