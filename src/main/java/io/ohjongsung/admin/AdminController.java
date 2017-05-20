package io.ohjongsung.admin;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.ohjongsung.blog.BlogService;
import io.ohjongsung.blog.PostForm;
import io.ohjongsung.blog.author.entity.MemberProfile;
import io.ohjongsung.blog.author.repository.TeamRepository;
import io.ohjongsung.blog.entity.Post;
import io.ohjongsung.blog.support.PostCategory;
import io.ohjongsung.blog.support.PostFormat;
import io.ohjongsung.blog.support.PostView;
import io.ohjongsung.support.DateFactory;
import io.ohjongsung.support.nav.PageableFactory;
import io.ohjongsung.support.nav.PaginationInfo;

/**
 * Created by ohjongsung on 2017-05-10.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private BlogService service;
    private TeamRepository teamRepository;
    private DateFactory dateFactory;

    @Value("${file.upload.folder}")
    private String base;

    private static final SimpleDateFormat SUB_PATH_DATE_FORMAT = new SimpleDateFormat("/yyyy/MM/dd/");

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
    @Transactional
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
    @Transactional
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
    @Transactional
    public String deletePost(@PathVariable Long postId) {
        Post post = service.getPost(postId);
        service.deletePost(post);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    public @ResponseBody LinkedList<FileMeta> upload(MultipartHttpServletRequest request,
                                                     HttpServletResponse response) {
        LinkedList<FileMeta> files = new LinkedList<FileMeta>();
        FileMeta fileMeta = null;

        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;

        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());

            if (files.size() >= 10) {
                files.pop();
            }

            fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
            fileMeta.setFileType(mpf.getContentType());
            String subPh = SUB_PATH_DATE_FORMAT.format(dateFactory.now());
            String filePh = base + subPh;
            fileMeta.setFileUrl(filePh + mpf.getOriginalFilename());

            try {
                File imgFile = new File(filePh);
                if (!imgFile.exists()) {
                    imgFile.mkdirs();
                }
                fileMeta.setBytes(mpf.getBytes());
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(fileMeta.getFileUrl()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            files.add(fileMeta);

        }

        return files;
    }

    @RequestMapping(value = "resummarize", method = POST)
    @Transactional
    public String resummarizeAllBlogPosts() {
        service.resummarizeAllPosts();
        return "redirect:/admin";
    }

    @RequestMapping(value = "refreshblogposts", method = POST)
    @Transactional
    @ResponseBody
    public String refreshBlogPosts(
            @RequestParam(value="page", defaultValue = "1", required = false) int page,
            @RequestParam(value="size", defaultValue = "10", required = false) int size) {
        Page<Post> posts = service.refreshPosts(page, size);
        return String.format("{page: %s, pageSize: %s, totalPages: %s, totalElements: %s}",
                posts.getNumber(), posts.getSize(), posts.getTotalPages(), posts.getTotalElements());
    }
}
