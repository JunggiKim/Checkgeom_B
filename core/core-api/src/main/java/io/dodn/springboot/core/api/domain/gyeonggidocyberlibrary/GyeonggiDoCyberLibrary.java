package io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary;

import io.dodn.springboot.core.api.domain.SearchType;

public class GyeonggiDoCyberLibrary {

    private static final String BASIC_SEARCH_URL = "https://ebook.library.kr/search?listType=list&keyword=";
    private static final String MORE_VIEW_SEARCH_URL = "https://ebook.library.kr/search/type?searchType=all&listType=list&asc=desc&keyword=";
    public static final String STAY_CSS = "h4.summaryHeading i";
    public static final String CONTENT_TYPE_URL = "&contentType=";
    public static final String SIZE = "&size=";


    public static String basicSearchUrlCreate(String searchKeyword)  {
        return BASIC_SEARCH_URL + (searchKeyword)
            + "&searchType= " + "all";
    }

    public static String moreViewSearchUrlCreate(String keyword ,GyeonggiDoCyberLibraryMoreViewType viewType) {
        return new StringBuilder().append(MORE_VIEW_SEARCH_URL).append(keyword)
                .append(CONTENT_TYPE_URL).append(viewType.bookType().getUrlType())
                .append(SIZE).append(viewType.totalCount())
                .toString();
    }
}
