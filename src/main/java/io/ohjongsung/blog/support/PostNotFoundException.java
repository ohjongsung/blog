package io.ohjongsung.blog.support;

import io.ohjongsung.support.ResourceNotFoundException;

/**
 * Created by ohjongsung on 2017-05-07. 포스트가 존재하지 않거나, 더 이상 게시되지 않을 때 발생
 */
@SuppressWarnings("serial")
public class PostNotFoundException extends ResourceNotFoundException {

    public PostNotFoundException(long id) {
        super("Could not find blog post with id " + id);
    }

    public PostNotFoundException(String slug) {
        super("Could not find blog post with slug '" + slug + "'");
    }
}
