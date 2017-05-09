package io.ohjongsung.support.nav;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ohjongsung on 2017-05-09. 뷰단에서 페이지 제어(PageElementsBuilder)를 그릴때 사용
 */
public class PaginationInfo {
    private final long currentPage;
    private final long totalPages;

    public PaginationInfo(Page<?> page) {
        currentPage = page.getNumber() + 1;
        totalPages = page.getTotalPages();
    }

    public boolean isVisible() {
        return isPreviousVisible() || isNextVisible();
    }

    public boolean isPreviousVisible() {
        return currentPage > 1;
    }

    public boolean isNextVisible() {
        return currentPage < totalPages;
    }

    public long getNextPageNumber() {
        return currentPage + 1;
    }

    public long getPreviousPageNumber() {
        return currentPage - 1;
    }

    public List<PageElement> getPageElements() {
        return new PageElementsBuilder(currentPage, totalPages).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PaginationInfo that = (PaginationInfo) o;

        if (currentPage != that.currentPage)
            return false;
        if (totalPages != that.totalPages)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (currentPage ^ (currentPage >>> 32));
        result = 31 * result + (int) (totalPages ^ (totalPages >>> 32));
        return result;
    }
}
