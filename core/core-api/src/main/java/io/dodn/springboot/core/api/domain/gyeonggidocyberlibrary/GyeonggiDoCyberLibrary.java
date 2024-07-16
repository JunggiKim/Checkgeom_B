package io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary;

import static io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryUrlEnum.*;

public class GyeonggiDoCyberLibrary {

    private static final String basicSearchURL = "https://ebook.library.kr/search?";
    public static final String stayClassName = "searchResultBody";



    private GyeonggiDoCyberLibraryBookType gyeonggiDoCyberLibraryBookType;

    public static String basicSearchUrlCreate(String keyword, String searchType, String listType, String sort) {
        return new StringBuilder().append(basicSearchURL)
            .append(GyeonggiDoCyberLibraryUrlEnum.keyword.getText()).append(keyword)
            .append(GyeonggiDoCyberLibraryUrlEnum.searchType.getText()).append(searchType)
            .append(GyeonggiDoCyberLibraryUrlEnum.listType.getText()).append(listType)
            .append(GyeonggiDoCyberLibraryUrlEnum.sort.getText()).append(sort)
            .toString();
    }

    public static String moreViewSearchUrlCreate(String basicUrl ,GyeonggiDoCyberLibraryMoreViewType viewType) {
        return new StringBuilder().append(basicUrl)
                .append(contentType.getText()).append(viewType.bookType().getUrlType())
                .append(asc.getText()).append("desc")
                .append(size.getText()).append(viewType.totalCount())
                .toString();
    }
//    &contentType=SUBS&asc=desc&size=263
}
