package io.ohjongsung.blog;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.ohjongsung.blog.author.repository.TeamRepository;
import io.ohjongsung.blog.entity.Post;
import io.ohjongsung.blog.entity.PostBuilder;
import io.ohjongsung.blog.support.PostCategory;
import io.ohjongsung.support.DateFactory;
import io.ohjongsung.support.DateTestUtils;

/**
 * Created by ohjongsung on 2017-05-07.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostFormAdapterTest_UpdatePostTest {

    public static final String RENDERED_HTML = "<p>Rendered HTML</p><p>from Markdown</p>";
    public static final String SUMMARY = "<p>Rendered HTML</p>";
    private Post post;
    private String title = "Title";
    private String content = "Rendered HTML\n\nfrom Markdown";
    private PostCategory category = PostCategory.HOW_TO_GUIDES;
    private boolean broadcast = true;
    private boolean draft = false;
    private Date publishAt = DateTestUtils.getDate("2017-05-07 12:00");
    private Date now = DateTestUtils.getDate("2017-05-07 13:00");
    private PostForm postForm;
    private String ORIGINAL_AUTHOR = "original author";

    @Mock
    private DateFactory dateFactory;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private PostSummary postSummary;
    @Mock
    private PostContentRenderer renderer;

    private PostFormAdapter postFormAdapter;

    @Before
    public void setUp() throws Exception {
        given(dateFactory.now()).willReturn(now);
        given(postSummary.forContent(anyString(), anyInt())).willReturn(SUMMARY);
        given(renderer.render(content)).willReturn(RENDERED_HTML);

        post = PostBuilder.post().id(123L).author("author_id", ORIGINAL_AUTHOR).build();

        postForm = new PostForm(post);
        postForm.setTitle(title);
        postForm.setContent(content);
        postForm.setCategory(category);
        postForm.setBroadcast(broadcast);
        postForm.setPublishAt(publishAt);

        postFormAdapter = new PostFormAdapter(renderer, postSummary, dateFactory, teamRepository);
        postFormAdapter.updatePostFromPostForm(post, postForm);
    }

    public void postHasCorrectUserEnteredValues() {
        assertThat(post.getTitle(), equalTo(title));
        assertThat(post.getRawContent(), equalTo(content));
        assertThat(post.getCategory(), equalTo(category));
        assertThat(post.isBroadcast(), equalTo(broadcast));
        assertThat(post.isDraft(), equalTo(draft));
        assertThat(post.getPublishAt(), equalTo(publishAt));
    }

    @Test
    public void postRetainsOriginalAuthor() {
        assertThat(post.getAuthor().getName(), equalTo(ORIGINAL_AUTHOR));
    }

    @Test
    public void postHasRenderedContent() {
        assertThat(post.getRenderedContent(), equalTo(RENDERED_HTML));
    }

    @Test
    public void postHasRenderedSummary() {
        assertThat(post.getRenderedSummary(), equalTo(SUMMARY));
    }

    @Test
    public void draftWithNullPublishDate() {
        postForm.setDraft(true);
        postForm.setPublishAt(null);
        postFormAdapter.updatePostFromPostForm(post, postForm);
        assertThat(post.getPublishAt(), is(nullValue()));
    }

    @Test
    public void postWithNullPublishDateSetsPublishAtToNow() {
        postForm.setDraft(false);
        postForm.setPublishAt(null);
        postFormAdapter.updatePostFromPostForm(post, postForm);
        assertThat(post.getPublishAt(), equalTo(now));
    }

    @Test
    public void updatingABlogPostDoesNotChangeItsCreatedDateByDefault() throws Exception {
        Date originalDate = DateTestUtils.getDate("2017-05-01 07:00");
        Post post = PostBuilder.post().createdAt(originalDate).build();
        postFormAdapter.updatePostFromPostForm(post, postForm);
        assertThat(post.getCreatedAt(), is(originalDate));
    }

    @Test
    public void updatingABlogPostUsesTheCreatedDateFromThePostFormIfPresent() throws Exception {
        Date originalDate = DateTestUtils.getDate("2017-05-01 07:00");
        Post post = PostBuilder.post().createdAt(originalDate).build();

        Date newDate = DateTestUtils.getDate("2017-05-07 03:00");
        postForm.setCreatedAt(newDate);

        postFormAdapter.updatePostFromPostForm(post, postForm);
        assertThat(post.getCreatedAt(), is(newDate));
    }
}