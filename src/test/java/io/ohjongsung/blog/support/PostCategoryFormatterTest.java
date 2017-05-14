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
        assertThat(formatter.parse(PostCategory.CONCEPT.getUrlSlug(), null), equalTo(PostCategory.CONCEPT));
    }

    @Test
    public void itConvertsEnumNameStringsToPostCategories() throws ParseException {
        assertThat(formatter.parse(PostCategory.CONCEPT.name(), null), equalTo(PostCategory.CONCEPT));
    }

    @Test
    public void itPrintsAStringThatCanBeParsed() throws ParseException {
        assertThat(formatter.parse(formatter.print(PostCategory.CONCEPT, null), null),
                equalTo(PostCategory.CONCEPT));
    }
}