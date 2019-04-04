package io.ohjongsung.blog.support;

/**
 * Created by ohjongsung on 2017-05-07. 포스트 카테고리 목록
 */
public enum PostCategory {

    HOW_TO_GUIDES("How-to Guides", "how-to-guides"),
    TUTORIALS("Tutorials", "tutorials"),
    EXPLANATION("Explanation", "explanation"),
    REFERENCE("Reference", "reference");

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
