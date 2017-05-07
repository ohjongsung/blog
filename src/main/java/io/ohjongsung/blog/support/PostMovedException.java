package io.ohjongsung.blog.support;

/**
 * Created by ohjongsung on 2017-05-07. post slug(URL 명) 가 변경 된 경우 발생
 */
@SuppressWarnings("serial")
public class PostMovedException extends RuntimeException {

    private String publicSlug;

    public PostMovedException(String publicSlug) {
        this.publicSlug = publicSlug;
    }

    public String getPublicSlug() {
        return publicSlug;
    }
}
