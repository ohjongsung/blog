package io.ohjongsung.blog.support;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.ohjongsung.blog.author.entity.MemberProfile;
import io.ohjongsung.blog.entity.Post;
import io.ohjongsung.support.DateFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * Created by ohjongsung on 2017-05-09. 뷰단에서 블로그 글을 그리기 위한 VO
 */
public class PostView {
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy");

    private final Post post;
    private final DateFactory dateFactory;

    private PostView(Post post, DateFactory dateFactory) {
        this.post = post;
        this.dateFactory = dateFactory;
    }

    public static PostView of(Post post, DateFactory dateFactory) {
        return new PostView(post, dateFactory);
    }

    public static Page<PostView> pageOf(Page<Post> posts, DateFactory dateFactory) {
        List<PostView> postViews = posts.getContent().stream()
                .map(post -> of(post, dateFactory))
                .collect(Collectors.toList());
        PageRequest pageRequest = new PageRequest(posts.getNumber(), posts.getSize(), posts.getSort());
        return new PageImpl<>(postViews, pageRequest, posts.getTotalElements());
    }

    public String getFormattedPublishDate() {
        return post.isScheduled() ? "Unscheduled" : DATE_FORMAT.format(post.getPublishAt());
    }

    public String getPath() {
        String path;
        if (post.isLiveOn(dateFactory.now())) {
            path = "/" + post.getPublicSlug();
        } else {
            path = "/admin/" + post.getAdminSlug();
        }
        return path;
    }

    public String getTitle() {
        return post.getTitle();
    }

    public boolean isScheduled() {
        return post.isScheduled();
    }

    public boolean isDraft() {
        return post.isDraft();
    }

    public PostCategory getCategory() {
        return post.getCategory();
    }

    public boolean isBroadcast() {
        return post.isBroadcast();
    }

    public MemberProfile getAuthor() {
        return post.getAuthor();
    }

    public String getRenderedSummary() {
        return post.getRenderedSummary();
    }

    public String getRenderedContent() {
        return post.getRenderedContent();
    }

    public Date getPublishAt() {
        return post.getPublishAt();
    }

    public Date getCreatedAt() {
        return post.getCreatedAt();
    }

    public Long getId() {
        return post.getId();
    }

    public boolean showReadMore() {
        return !post.getRenderedContent().equals(post.getRenderedSummary());
    }

    public String getEditPath() {
        return getUpdatePath() + "/edit";
    }

    public String getUpdatePath() {
        return "/admin/" + post.getAdminSlug();
    }
}
