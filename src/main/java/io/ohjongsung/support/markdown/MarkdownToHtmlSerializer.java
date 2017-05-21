package io.ohjongsung.support.markdown;

import java.util.Map;

import org.pegdown.LinkRenderer;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.HeaderNode;
import org.springframework.util.StringUtils;

/**
 * Created by ohjongsung on 2017-05-07. 제목 HTML 전환 커스텀을 위한 확장 클래스이다. a 태그를 다음과 같이 만든다.
 * <h2 class="title"><a href="#this-is-a-title" class="anchor" name=
 * "this-is-a-title"></a>This is a title</h2>
 */
public class MarkdownToHtmlSerializer extends ToHtmlSerializer {
    public MarkdownToHtmlSerializer(final LinkRenderer linkRenderer,
                                    final Map<String, VerbatimSerializer> verbatimSerializers) {
        super(linkRenderer, verbatimSerializers);
    }

    @Override
    public void visit(HeaderNode node) {
        String tag = "h" + node.getLevel();
        String title = printChildrenToString(node);
        LinkRenderer.Rendering anchorLink = createAnchorLink(title);
        printer.print('<').print(tag).print('>');
        printLink(anchorLink);
        visitChildren(node);
        printer.print('<').print('/').print(tag).print('>');
    }

    @Override
    protected void printImageTag(LinkRenderer.Rendering rendering) {
        printer.print("<img");
        printAttribute("src", rendering.href);
        printAttribute("class", "w3-image");
        // shouldn't include the alt attribute if its empty
        if (!rendering.text.equals("")) {
            printAttribute("alt", rendering.text);
        }
        for (LinkRenderer.Attribute attr : rendering.attributes) {
            printAttribute(attr.name, attr.value);
        }
        printer.print(" />");
    }

    private LinkRenderer.Rendering createAnchorLink(String title) {
        String cleanedTitle = title.toLowerCase().replace("\n", " ").replaceAll("[^a-z\\d\\s]", " ");
        String slug = StringUtils.arrayToDelimitedString(StringUtils.tokenizeToStringArray(cleanedTitle, " "), "-");
        return new LinkRenderer.Rendering("#" + slug, "").withAttribute("class", "anchor").withAttribute("name", slug);
    }
}
