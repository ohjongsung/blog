package io.ohjongsung.blog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ohjongsung on 2017-05-07. 마크다운 관련 서비스 테스트
 */
public class PegdownMarkdownServiceTest {

    private PegdownMarkdownService service;

    @Before
    public void setUp() throws Exception {
        service = new PegdownMarkdownService();
    }

    @Test
    public void renderLink() throws Exception {
        String markdown = "[my link](http://spring.io)";
        Assert.assertEquals("<p><a href=\"http://spring.io\">my link</a></p>", service.renderToHtml(markdown));
    }

    @Test
    public void renderFencedCodeBlock() throws Exception {
        String markdown = "```java\n" +
                "public static void main(String[] args) {}\n" +
                "```";
        Assert.assertEquals("<div class=\"w3-panel w3-border w3-light-grey\"><pre class=\"prettyprint java\"><code>public static void main(String[] args) {}\n" +
                "</code></pre></div>", service.renderToHtml(markdown));
    }

    @Test
    public void renderJavaScript() throws Exception {
        String markdown = "<script>alert('hello');</script>";
        Assert.assertEquals("<script>alert('hello');</script>", service.renderToHtml(markdown));
    }

    @Test
    public void renderRawHtml() throws Exception {
        String markdown = "raw html<span>inline</span>";
        Assert.assertEquals("<p>raw html<span>inline</span></p>", service.renderToHtml(markdown));
    }

    @Test
    public void renderTitleAnchors() throws Exception {
        String markdown = "### This is a title";
        Assert.assertEquals("<h3><a href=\"#this-is-a-title\" class=\"anchor\" name=\"this-is-a-title\"></a>This is a title</h3>",
                service.renderToHtml(markdown));
    }
}