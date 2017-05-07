package io.ohjongsung.blog;

import io.ohjongsung.support.markdown.MarkdownToHtmlSerializer;
import io.ohjongsung.support.markdown.PrettifyVerbatimSerializer;
import org.pegdown.*;
import org.pegdown.ast.RootNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Created by ohjongsung on 2017-05-07. 마크다운서비스 구현체
 */
@Service
@Qualifier("pegdown")
public class PegdownMarkdownService implements MarkdownService {

    private final PegDownProcessor pegdown;

    public PegdownMarkdownService() {
        pegdown = new PegDownProcessor(Extensions.ALL ^ Extensions.ANCHORLINKS);
    }

    @Override
    public String renderToHtml(String markdownSource) {
        synchronized (pegdown) {
            RootNode astRoot = pegdown.parseMarkdown(markdownSource.toCharArray());
            MarkdownToHtmlSerializer serializer = new MarkdownToHtmlSerializer(new LinkRenderer(),
                    Collections.singletonMap(VerbatimSerializer.DEFAULT, PrettifyVerbatimSerializer.INSTANCE));
            return serializer.toHtml(astRoot);
        }
    }
}
