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

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by ohjongsung on 2017-05-08.
 */
@RunWith(MockitoJUnitRunner.class)
public class BlogServiceTest_UpdatePostTest {

    private Post post;
    private Date publishAt = DateTestUtils.getDate("2017-07-08 12:00");
    private Date now = DateTestUtils.getDate("2017-07-08 13:00");

    @Mock
    private PostRepository postRepository;
    @Mock
    private DateFactory dateFactory;
    @Mock
    private PostFormAdapter postFormAdapter;
    @Rule
    public ExpectedException expected = ExpectedException.none();

    private PostForm postForm;
    private BlogService service;

    @Before
    public void setUp() throws Exception {
        given(dateFactory.now()).willReturn(now);

        service = new BlogService(postRepository, postFormAdapter, dateFactory);

        post = PostBuilder.post().id(123L).publishAt(publishAt).build();

        postForm = new PostForm(post);
        service.updatePost(post, postForm);
    }

    @Test
    public void postIsUpdated() {
        verify(postFormAdapter).updatePostFromPostForm(post, postForm);
    }

    @Test
    public void postIsPersisted() {
        verify(postRepository).save(post);
    }
}