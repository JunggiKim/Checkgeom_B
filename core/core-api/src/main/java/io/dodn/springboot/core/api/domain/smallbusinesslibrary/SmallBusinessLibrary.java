package io.dodn.springboot.core.api.domain.smallbusinesslibrary;


public class SmallBusinessLibrary {

    private static final String BASIC_SEARCH_URL = "https://semas.dkyobobook.co.kr/search/searchList.ink?schClst=all&schDvsn=001&orderByKey=&schTxt=";
    private static final String MORE_VIEW_URL =
            "https://semas.dkyobobook.co.kr/search/searchList.ink?schClst=all&schDvsn=001&orderByKey=&reSchTxt=&pageIndex=1&recordCount=20";

    public static final String ROW_COUNT = "&selViewCnt=";
    public static final String SEARCH_TEXT = "&schTxt=";

    public static String basicUrlCreate(String searchKeyword) {
            return BASIC_SEARCH_URL.concat(searchKeyword);
    }



    public static String moreViewUrlCreate(String searchKeyword , String totalSearchCount) {
        return new StringBuilder(MORE_VIEW_URL)
                .append(SEARCH_TEXT).append(searchKeyword)
                .append(ROW_COUNT).append(totalSearchCount)
                .toString();
    }




}
