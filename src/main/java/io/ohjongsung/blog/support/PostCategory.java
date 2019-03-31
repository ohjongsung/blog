package io.ohjongsung.blog.support;

/**
 * Created by ohjongsung on 2017-05-07. 포스트 카테고리 목록
 */
public enum PostCategory {

    HOW_TO_GUIDES("How-to Guides", "How-to Guides"),
    TUTORIALS("Tutorials", "Tutorials"),
    EXPLANATION("Explanation", "Explanation"),
    REFERENCE("Reference", "Reference");

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
