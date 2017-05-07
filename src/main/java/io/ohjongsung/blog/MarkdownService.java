package io.ohjongsung.blog;

/**
 * Created by ohjongsung on 2017-05-07. 마크다운 인터페이스
 */
public interface MarkdownService {
    String renderToHtml(String markdownSource);
}
