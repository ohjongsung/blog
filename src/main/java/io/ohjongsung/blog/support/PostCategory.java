package io.ohjongsung.blog.support;

/**
 * Created by ohjongsung on 2017-05-07. 포스트 카테고리 목록
 */
public enum PostCategory {

    INTRODUCTION("Introduction", "소개"),
    CONCEPT("Concept", "개념"),
    PRACTICE("Practice", "방법"),
    TIP("Tip", "팁"),
    REVIEW("Review", "서평");

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
