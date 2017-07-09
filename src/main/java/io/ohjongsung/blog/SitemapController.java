package io.ohjongsung.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.ohjongsung.support.sitemap.XmlUrlSet;

/**
 * Created by ohjongsung on 2017-07-08.
 */
@RestController
public class SitemapController {

    private final SitemapService service;

    @Autowired
    public SitemapController(SitemapService service) {
        this.service = service;
    }

    @RequestMapping(value = "/sitemap.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public XmlUrlSet sitemap() {
        return service.createSitemap();
    }
}
