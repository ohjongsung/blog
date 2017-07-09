package io.ohjongsung.support.sitemap;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by ohjongsung on 2017-07-08.
 */
@JacksonXmlRootElement(localName = "urlset", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
public class XmlUrlSet {

    @JacksonXmlProperty(localName = "url", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<XmlUrl> xmlUrls = new ArrayList<>();

    public void addUrl(XmlUrl xmlUrl) {
        xmlUrls.add(xmlUrl);
    }

    public List<XmlUrl> getXmlUrls() {
        return xmlUrls;
    }
}
