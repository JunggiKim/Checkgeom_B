package io.dodn.springboot.core.api.domain.smallbusinesslibrary;


public class SmallBusinessLibrary {


    private static final String BASIC_SEARCH_URL = "https://semas.dkyobobook.co.kr/search/searchList.ink?schClst=all&schDvsn=001&orderByKey=&schTxt=";
    public static final String ROW_COUNT = "&rowCount=";


    public static String basicUrlCreate(String searchKeyword) {
            return new StringBuilder(BASIC_SEARCH_URL).append(searchKeyword).toString();
    }



    public static String moreViewUrlCreate(String basicUrl , int totalSearchCount) {
        return new StringBuilder(basicUrl)
                .append(ROW_COUNT).append(totalSearchCount).toString();
    }




}
