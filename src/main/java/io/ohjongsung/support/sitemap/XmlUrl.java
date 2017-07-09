package io.ohjongsung.support.sitemap;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by ohjongsung on 2017-07-08.
 */
@JacksonXmlRootElement(localName = "url", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
public class XmlUrl {

    private static final String BASE_URL = "https://ohjongsung.io/";
    private static final SimpleDateFormat SITE_MAP_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @JacksonXmlProperty(localName = "loc", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String loc;

    @JacksonXmlProperty(localName = "lastmod", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String lastmod;

    @JacksonXmlProperty(localName = "changefreq", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String changefreq = "daily";

    @JacksonXmlProperty(localName = "priority", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String priority = "0.5";

    public XmlUrl(String loc, Date lastmod) {
        this.loc = BASE_URL + loc;
        this.lastmod = SITE_MAP_DATE_FORMAT.format(lastmod);
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getLastmod() {
        return lastmod;
    }

    public void setLastmod(String lastmod) {
        this.lastmod = lastmod;
    }

    public String getChangefreq() {
        return changefreq;
    }

    public void setChangefreq(String changefreq) {
        this.changefreq = changefreq;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
