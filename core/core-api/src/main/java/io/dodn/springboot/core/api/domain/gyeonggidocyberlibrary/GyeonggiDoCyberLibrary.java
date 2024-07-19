package io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary;

import static io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryUrlEnum.*;

public class GyeonggiDoCyberLibrary {

    private static final String basicSearchURL = "https://ebook.library.kr/search?searchType=all&listType=list&keyword=";
    private static final String moreViewSearchURL = "https://ebook.library.kr/search/type?searchType=all&listType=list&asc=desc&keyword=";
    public static final String stayClassName = "searchResultBody";

    public static String basicSearchUrlCreate(String keyword) {
        return basicSearchURL.concat(keyword);
    }

    public static String moreViewSearchUrlCreate(String keyword ,GyeonggiDoCyberLibraryMoreViewType viewType) {
        return new StringBuilder().append(moreViewSearchURL).append(keyword)
                .append(contentType.getText()).append(viewType.bookType().getUrlType())
                .append(size.getText()).append(viewType.totalCount())
                .toString();
    }
//    &contentType=SUBS&asc=desc&size=263
}
