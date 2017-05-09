package io.ohjongsung.blog;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import io.ohjongsung.blog.entity.Post;
import io.ohjongsung.blog.support.PostCategory;
import io.ohjongsung.blog.support.PostView;
import io.ohjongsung.support.nav.PageableFactory;
import io.ohjongsung.support.nav.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.ohjongsung.support.DateFactory;

import java.util.List;

/**
 * Created by ohjongsung on 2017-05-08.
 */
@Controller
@RequestMapping("/")
public class BlogController {
    private final BlogService service;
    private final DateFactory dateFactory;

    @Autowired
    public BlogController(BlogService service, DateFactory dateFactory) {
        this.service = service;
        this.dateFactory = dateFactory;
    }

    @RequestMapping(value = "", method = { GET, HEAD })
    public String listPublishedPosts(Model model, @RequestParam(defaultValue = "1") int page) {
        Pageable pageRequest = PageableFactory.forLists(page);
        Page<Post> result = service.getPublishedPosts(pageRequest);
        return renderListOfPosts(result, model, "All Posts");
    }

    @RequestMapping(value = "/category/{category}", method = { GET, HEAD })
    public String listPublishedPostsForCategory(@PathVariable("category") PostCategory category, Model model,
                                                @RequestParam(defaultValue = "1", value = "page") int page) {
        Pageable pageRequest = PageableFactory.forLists(page);
        Page<Post> result = service.getPublishedPosts(category, pageRequest);
        return renderListOfPosts(result, model, category.getDisplayName());
    }

    private String renderListOfPosts(Page<Post> page, Model model, String activeCategory) {
        Page<PostView> postViewPage = PostView.pageOf(page, dateFactory);
        List<PostView> posts = postViewPage.getContent();
        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("categories", PostCategory.values());
        model.addAttribute("posts", posts);
        model.addAttribute("paginationInfo", new PaginationInfo(postViewPage));
        return "index";
    }
}
