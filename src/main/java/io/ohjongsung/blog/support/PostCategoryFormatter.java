package io.ohjongsung.blog.support;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by ohjongsung on 2017-05-14. enum 타입을 스트링 값으로 포맷팅 해준다
 */
@Component
public class PostCategoryFormatter implements Formatter<PostCategory> {

    private static Map<String, PostCategory> mapping = new HashMap<>();

    public PostCategoryFormatter() {
        for (PostCategory category : PostCategory.values()) {
            mapping.put(category.getUrlSlug(), category);
            mapping.put(category.name(), category);
        }
    }

    @Override
    public PostCategory parse(String text, Locale locale) throws ParseException {
        return mapping.get(text.trim());
    }

    @Override
    public String print(PostCategory category, Locale locale) {
        return category.getUrlSlug();
    }
}
