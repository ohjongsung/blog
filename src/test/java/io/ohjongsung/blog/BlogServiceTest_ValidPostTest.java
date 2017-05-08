package io.ohjongsung.blog;

import io.ohjongsung.blog.entity.Post;
import io.ohjongsung.blog.entity.PostBuilder;
import io.ohjongsung.blog.repository.PostRepository;
import io.ohjongsung.support.DateFactory;
import io.ohjongsung.support.DateTestUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;

/**
 * Created by ohjongsung on 2017-05-08.
 */
@RunWith(MockitoJUnitRunner.class)
public class BlogServiceTest_ValidPostTest {

    private static final String AUTHOR_USERNAME = "username";
    private Post post;
    private PostForm postForm = new PostForm();
    private Date publishAt = DateTestUtils.getDate("2017-07-08 12:00");
    private Date now = DateTestUtils.getDate("2017-07-08 13:00");

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostFormAdapter postFormAdapter;
    @Mock
    private DateFactory dateFactory;
    @Rule
    public ExpectedException expected = ExpectedException.none();

    private BlogService service;

    @Before
    public void setUp() throws Exception {
        given(dateFactory.now()).willReturn(now);

        given(postRepository.save((Post) anyObject())).will(invocation -> {
            Post post = (Post) invocation.getArguments()[0];
            ReflectionTestUtils.setField(post, "id", 123L);
            return post;
        });

        post = PostBuilder.post().publishAt(publishAt).build();
        given(postFormAdapter.createPostFromPostForm(postForm, AUTHOR_USERNAME)).willReturn(post);

        service = new BlogService(postRepository, postFormAdapter, dateFactory);
        service.addPost(postForm, AUTHOR_USERNAME);
    }

    @Test
    public void createsAPost() {
        verify(postFormAdapter).createPostFromPostForm(postForm, AUTHOR_USERNAME);
    }

    @Test
    public void postIsPersisted() {
        verify(postRepository).save((Post) anyObject());
    }
}