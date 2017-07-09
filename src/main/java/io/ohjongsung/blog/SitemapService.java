package io.ohjongsung.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ohjongsung.blog.entity.Post;
import io.ohjongsung.support.sitemap.XmlUrl;
import io.ohjongsung.support.sitemap.XmlUrlSet;

/**
 * Created by ohjongsung on 2017-07-08.
 */
@Service
public class SitemapService {

    private final BlogService service;

    @Autowired
    public SitemapService(BlogService service) {
        this.service = service;
    }

    public XmlUrlSet createSitemap() {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        List<Post> posts = service.getAllPublishedPosts();
        for (Post item : posts) {
            xmlUrlSet.addUrl(new XmlUrl(item.getPublicSlug(), item.getPublishAt()));
        }

        return xmlUrlSet;
    }

}
