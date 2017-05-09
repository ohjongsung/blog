package io.ohjongsung.support.nav;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by ohjongsung on 2017-05-09. PageRequest를 생성하기 위한 유틸 추상 클래스
 */
public abstract class PageableFactory {

    public static Pageable all() {
        return build(0, Integer.MAX_VALUE);
    }

    public static Pageable forLists(int page) {
        return build(page - 1, 10);
    }

    public static Pageable forDashboard(int page) {
        return build(page - 1, 30);
    }

    public static Pageable forFeeds() {
        return build(0, 20);
    }

    public static Pageable forSearch(int page) {
        return new PageRequest(page - 1, 10);
    }

    private static Pageable build(int page, int pageSize) {
        return new PageRequest(page, pageSize, Sort.Direction.DESC, "publishAt");
    }
}
