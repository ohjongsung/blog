package io.ohjongsung.blog;


import io.ohjongsung.blog.entity.Post;
import io.ohjongsung.blog.support.PostCategory;
import io.ohjongsung.blog.support.PostFormat;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ohjongsung on 2017-05-07. 포스트 작성 폼
 */
public class PostForm {
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    private PostCategory category;

    @NotNull
    private PostFormat format;

    private boolean broadcast;
    private boolean draft;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date publishAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createdAt;

    public PostForm() {
    }

    public PostForm(Post post) {
        title = post.getTitle();
        content = post.getRawContent();
        category = post.getCategory();
        broadcast = post.isBroadcast();
        draft = post.isDraft();
        publishAt = post.getPublishAt();
        format = post.getFormat();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostCategory getCategory() {
        return category;
    }

    public void setCategory(PostCategory category) {
        this.category = category;
    }

    public PostFormat getFormat() {
        return format;
    }

    public void setFormat(PostFormat format) {
        this.format = format;
    }

    public boolean isBroadcast() {
        return broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
