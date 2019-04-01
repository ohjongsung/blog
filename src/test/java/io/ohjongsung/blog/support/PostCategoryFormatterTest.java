package io.ohjongsung.blog.support;

import org.junit.Test;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by ohjongsung on 2017-05-14.
 */
public class PostCategoryFormatterTest {
    private PostCategoryFormatter formatter = new PostCategoryFormatter();

    @Test
    public void itConvertsUrlSlugStringsToPostCategories() throws ParseException {
        assertThat(formatter.parse(PostCategory.HOW_TO_GUIDES.getUrlSlug(), null), equalTo(PostCategory.HOW_TO_GUIDES));
    }

    @Test
    public void itConvertsEnumNameStringsToPostCategories() throws ParseException {
        assertThat(formatter.parse(PostCategory.HOW_TO_GUIDES.name(), null), equalTo(PostCategory.HOW_TO_GUIDES));
    }

    @Test
    public void itPrintsAStringThatCanBeParsed() throws ParseException {
        assertThat(formatter.parse(formatter.print(PostCategory.HOW_TO_GUIDES, null), null),
                equalTo(PostCategory.HOW_TO_GUIDES));
    }
}