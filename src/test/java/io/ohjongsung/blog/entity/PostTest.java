package io.ohjongsung.blog.entity;

import org.junit.Ignore;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

/**
 * Created by ohjongsung on 2017-05-07. 포스트 클래스 테스트
 */
public class PostTest {
    private PostBuilder builder = PostBuilder.post().id(1L);

    @Test
    public void slugReplacesSpacesWithDashes() {
        assertEquals("1-this-is-a-title", builder.title("this is a title").build().getAdminSlug());
    }

    @Test
    public void slugReplacesMultipleSpacesWithASingleDash() {
        assertEquals("1-this-is-a-title", builder.title("This    is a title").build().getAdminSlug());
    }

    @Test
    public void slugStripsNonAlphanumericCharacters() {
        assertEquals("1-title-1-with-characters", builder.title("Title 1, with characters';:\\|").build()
                .getAdminSlug());
    }

    @Test
    public void slugStripsNonAlphanumericCharactersUsedAsDividersWithSpaces() {
        assertEquals("1-title-1-something", builder.title("Title__--1/@something").build().getAdminSlug());
    }

    @Test
    public void slugStripsNewLineCharacters() {
        assertEquals("1-title-1-on-multiple-lines", builder.title("Title 1\n on multiple\nlines").build()
                .getAdminSlug());
    }

    @Test
    public void isNotLiveIfDraft() throws ParseException {
        Post post = PostBuilder.post().draft().build();
        assertThat(post.isLiveOn(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2017-05-07 00:00")), is(false));
    }

    @Test
    public void isNotLiveIfScheduledInTheFuture() throws ParseException {
        Post post = PostBuilder.post().publishAt("2017-12-12 00:00").build();
        assertThat(post.isLiveOn(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2017-05-07 00:00")), is(false));
    }

    @Ignore("TODO: implement this at some point")
    @Test
    public void isScheduledIfPublishDateIsInTheFuture() throws ParseException {
        Date futureDate = new Date(System.currentTimeMillis() + 10000000);
        Post post = PostBuilder.post().publishAt(futureDate).build();
        assertThat(post.isScheduled(), is(true));
    }

    @Test
    public void isLiveIfPublishedInThePast() throws ParseException {
        Post post = PostBuilder.post().publishAt("2017-05-06 00:00").build();
        assertThat(post.isLiveOn(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2017-05-28 00:00")), is(true));
    }

    @Test
    public void isLiveIfPublishedNow() throws ParseException {
        Post post = PostBuilder.post().publishAt("2017-05-07 00:00").build();
        assertThat(post.isLiveOn(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2017-05-07 00:00")), is(true));
    }
}