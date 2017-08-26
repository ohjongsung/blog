package io.ohjongsung.blog;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import io.ohjongsung.blog.entity.Post;
import io.ohjongsung.blog.support.PostCategory;
import io.ohjongsung.blog.support.PostMovedException;
import io.ohjongsung.blog.support.PostNotFoundException;
import io.ohjongsung.blog.support.PostView;
import io.ohjongsung.support.DateFactory;
import io.ohjongsung.support.nav.PageableFactory;
import io.ohjongsung.support.nav.PaginationInfo;

/**
 * Created by ohjongsung on 2017-05-08.
 */
@Controller
public class BlogController {
    private final BlogService service;
    private final DateFactory dateFactory;

    @Autowired
    public BlogController(BlogService service, DateFactory dateFactory) {
        this.service = service;
        this.dateFactory = dateFactory;
    }

    @RequestMapping(value = "/{year:\\d+}/{month:\\d+}/{day:\\d+}/{slug}", method = { GET, HEAD })
    public String showPost(@PathVariable String year, @PathVariable String month, @PathVariable String day,
                           @PathVariable String slug, Model model) {

        String publicSlug = String.format("%s/%s/%s/%s", year, month, day, slug);
        Post post = service.getPublishedPost(publicSlug);
        model.addAttribute("post", PostView.of(post, dateFactory));
        model.addAttribute("categories", PostCategory.values());
        model.addAttribute("activeCategory", post.getCategory().getDisplayName());
        return "show";
    }

    @RequestMapping(value = "", method = { GET, HEAD })
    public String listPublishedPosts(Model model, @RequestParam(defaultValue = "1") int page) {
        Pageable pageRequest = PageableFactory.forLists(page);
        Page<Post> result = service.getPublishedPosts(pageRequest);
        return renderListOfPosts(result, model, "All Posts");
    }

    @RequestMapping(value = "/pages/{page}", method = { GET, HEAD })
    public String listPublishedPostsWithPage(Model model, @PathVariable int page) {
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

    @RequestMapping(value = "/{year:\\d+}", method = { GET, HEAD })
    public String listPublishedPostsForYear(@PathVariable int year,
                                            @RequestParam(defaultValue = "1", value = "page") int page, Model model) {

        Pageable pageRequest = PageableFactory.forLists(page);
        Page<Post> result = service.getPublishedPostsByDate(year, pageRequest);
        model.addAttribute("title", String.format("Archive for %d", year));
        return renderListOfPosts(result, model, "All Posts");
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

    @ExceptionHandler
    public RedirectView handle(PostMovedException moved) {
        RedirectView redirect = new RedirectView();
        redirect.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        redirect.setUrl("/" + moved.getPublicSlug());
        return redirect;
    }

    @ExceptionHandler(PostNotFoundException.class)
    public String handle() {
        return "/pages/404";
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("/pages/500");
        return mav;
    }

}
