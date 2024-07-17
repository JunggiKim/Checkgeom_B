package io.dodn.springboot.core.api.domain.smallbusinesslibrary;


public class SmallBusinessLibrary {

    private static final String BASIC_SEARCH_URL = "https://semas.dkyobobook.co.kr/search/searchList.ink?schClst=all&schDvsn=001&orderByKey=&schTxt=";
    public static final String ROW_COUNT = "&rowCount=";

    public static String basicUrlCreate(String searchKeyword) {
            return BASIC_SEARCH_URL.concat(searchKeyword);
    }



    public static String moreViewUrlCreate(String basicUrl , String totalSearchCount) {
        return new StringBuilder(basicUrl)
                .append(ROW_COUNT).append(totalSearchCount).toString();
    }




}
