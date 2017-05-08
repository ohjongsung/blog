package io.ohjongsung.blog;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.ohjongsung.support.DateFactory;

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

        return "index";
    }
}
