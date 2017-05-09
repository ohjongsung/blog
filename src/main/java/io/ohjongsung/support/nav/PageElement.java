package io.ohjongsung.support.nav;

/**
 * Created by ohjongsung on 2017-05-09. 페이지 번호 배열의 단일 요소이다. 블로그 목록에서 뷰에서 페이지 번호를 의미하지만,
 * 현재 페이지 번호 나열의 범위를 벗어난 줄임표(...) 요소도 의미한다. 각 페이지 요소는 탐색 가능과 불가의 상태를 가진다.
 * 예를 들어, 현재 페이지와 추가 페이지를 나타내는 요소, 즉 줄임표(...)와 같은 것은 탐색 할 수 없는 상태이다.
 */
public class PageElement {
    private final String label;
    private final boolean isNavigable;
    private final boolean isCurrentPage;

    public PageElement(long pageNumber, boolean isNavigable, boolean isCurrentPage) {
        this(pageNumber + "", isNavigable, isCurrentPage);
    }

    public PageElement(String label, boolean isNavigable, boolean isCurrentPage) {
        this.label = label;
        this.isNavigable = isNavigable;
        this.isCurrentPage = isCurrentPage;
    }

    public String getLabel() {
        return label;
    }

    public boolean isNavigable() {
        return isNavigable;
    }

    public boolean isCurrentPage() {
        return isCurrentPage;
    }
}
