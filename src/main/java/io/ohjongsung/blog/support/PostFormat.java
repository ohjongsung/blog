package io.ohjongsung.blog.support;

/**
 * Created by ohjongsung on 2017-05-07. 포스트 포맷
 */
public enum PostFormat {

    MARKDOWN("Markdown", "markdown");

    private String displayName;
    private String slug;

    PostFormat(String displayName, String slug) {
        this.displayName = displayName;
        this.slug = slug;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSlug() {
        return slug;
    }

    public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
