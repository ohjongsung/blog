package io.ohjongsung.blog.support;

/**
 * Created by ohjongsung on 2017-05-07. 포스트 카테고리 목록
 */
public enum PostCategory {

    INTRODUCTION("소개", "소개"),
    CONCEPT("개념", "개념"),
    PRACTICE("방법", "방법"),
    TIP("팁", "팁"),
    REVIEW("서평", "서평");

    private String displayName;
    private String urlSlug;

    PostCategory(String displayName, String urlSlug) {
        this.displayName = displayName;
        this.urlSlug = urlSlug;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrlSlug() {
        return urlSlug;
    }

    public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
