package io.ohjongsung.admin;

import java.security.Principal;
import java.util.Collections;

import io.ohjongsung.blog.PostForm;
import io.ohjongsung.blog.author.entity.MemberProfile;
import io.ohjongsung.blog.entity.Post;
import io.ohjongsung.blog.support.PostCategory;
import io.ohjongsung.blog.support.PostFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.ohjongsung.blog.BlogService;
import io.ohjongsung.blog.author.repository.TeamRepository;
import io.ohjongsung.blog.support.PostView;
import io.ohjongsung.support.DateFactory;
import io.ohjongsung.support.nav.PageableFactory;
import io.ohjongsung.support.nav.PaginationInfo;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by ohjongsung on 2017-05-10.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private BlogService service;
    private TeamRepository teamRepository;
    private DateFactory dateFactory;

    @Autowired
    public AdminController(BlogService service, TeamRepository teamRepository, DateFactory dateFactory) {
        this.service = service;
        this.teamRepository = teamRepository;
        this.dateFactory = dateFactory;
    }

    @RequestMapping(value = "", method = { GET, HEAD })
    public String dashboard(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<PostView> postViewPage = PostView.pageOf(service.getPublishedPosts(PageableFactory.forDashboard(page)), dateFactory);
        model.addAttribute("posts", postViewPage);
        model.addAttribute("paginationInfo", new PaginationInfo(postViewPage));

        if(page == 1) {
            model.addAttribute("drafts", PostView.pageOf(service.getDraftPosts(PageableFactory.all()), dateFactory));
            model.addAttribute("scheduled", PostView.pageOf(service.getScheduledPosts(PageableFactory.all()), dateFactory));
        } else {
            Page<PostView> emptyPage = new PageImpl<PostView>(Collections.emptyList(), PageableFactory.all(), 0);
            model.addAttribute("drafts", emptyPage);
            model.addAttribute("scheduled", emptyPage);
        }

        return "admin/index";
    }

    @RequestMapping(value = "/new", method = { GET, HEAD })
    public String newPost(Model model) {
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("categories", PostCategory.values());
        model.addAttribute("formats", PostFormat.values());
        return "admin/new";
    }

    @RequestMapping(value = "/{postId:[0-9]+}{slug:.*}/edit", method = { GET, HEAD })
    public String editPost(@PathVariable Long postId, @PathVariable String slug, Model model) {
        Post post = service.getPost(postId);
        PostForm postForm = new PostForm(post);
        String path = PostView.of(post, dateFactory).getPath();

        model.addAttribute("categories", PostCategory.values());
        model.addAttribute("formats", PostFormat.values());
        model.addAttribute("postForm", postForm);
        model.addAttribute("post", post);
        model.addAttribute("path", path);
        return "admin/edit";
    }

    @RequestMapping(value = "/{postId:[0-9]+}{slug:.*}", method = { GET, HEAD })
    public String showPost(@PathVariable Long postId, @PathVariable String slug, Model model) {
        model.addAttribute("post", PostView.of(service.getPost(postId), dateFactory));
        return "admin/show";
    }

    @RequestMapping(value = "", method = { POST })
    public String createPost(Principal principal, @Valid PostForm postForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", PostCategory.values());
            model.addAttribute("formats", PostFormat.values());
            return "admin/new";
        } else {
            MemberProfile memberProfile = teamRepository.findById(new Long(principal.getName()));
            try {
                Post post = service.addPost(postForm, memberProfile.getUsername());
                PostView postView = PostView.of(post, dateFactory);
                return "redirect:" + postView.getPath() + "/edit";
            } catch (DataIntegrityViolationException ex) {
                model.addAttribute("categories", PostCategory.values());
                model.addAttribute("postForm", postForm);
                bindingResult.rejectValue("title", "duplicate_post",
                        "A blog post with this publication date and title already exists");
                return "admin/new";
            }
        }
    }

    @RequestMapping(value = "/{postId:[0-9]+}{slug:.*}/edit", method = PUT)
    public String updatePost(@PathVariable Long postId, @Valid PostForm postForm, BindingResult bindingResult,
                             Model model) {
        Post post = service.getPost(postId);
        if (!bindingResult.hasErrors()) {
            service.updatePost(post, postForm);
        }
        PostView postView = PostView.of(post, dateFactory);
        String path = postView.getPath();

        model.addAttribute("categories", PostCategory.values());
        model.addAttribute("formats", PostFormat.values());
        model.addAttribute("post", post);
        model.addAttribute("path", path);
        return "/admin/edit";
    }

    @RequestMapping(value = "/{postId:[0-9]+}{slug:.*}", method = DELETE)
    public String deletePost(@PathVariable Long postId) {
        Post post = service.getPost(postId);
        service.deletePost(post);
        return "redirect:/admin";
    }

    @RequestMapping(value = "resummarize", method = POST)
    public String resummarizeAllBlogPosts() {
        service.resummarizeAllPosts();
        return "redirect:/admin";
    }

    @RequestMapping(value = "refreshblogposts", method = POST)
    @ResponseBody
    public String refreshBlogPosts(
            @RequestParam(value="page", defaultValue = "1", required = false) int page,
            @RequestParam(value="size", defaultValue = "10", required = false) int size) {
        Page<Post> posts = service.refreshPosts(page, size);
        return String.format("{page: %s, pageSize: %s, totalPages: %s, totalElements: %s}",
                posts.getNumber(), posts.getSize(), posts.getTotalPages(), posts.getTotalElements());
    }
}
