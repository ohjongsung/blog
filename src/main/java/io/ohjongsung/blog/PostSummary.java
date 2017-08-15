package io.ohjongsung.blog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

/**
 * Created by ohjongsung on 2017-05-07. HTML 을 파싱해서 포스트 내용 요약을 만든다.
 */
@Service
public class PostSummary {

    public String forContent(String content, int maxLength) {
        Document document = Jsoup.parse(content);
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (Element element : document.body().children()) {
            builder.append(element.text());
            builder.append("\n");
            count += element.text().length();
            if (count >= maxLength) {
                break;
            }
        }

        return builder.toString();
    }
}
