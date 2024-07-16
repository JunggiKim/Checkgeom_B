package io.dodn.springboot.core.api.domain.gyeonggiEducationalElectronicLibrary;

public class gyeonggiEducationalElectronicLibrary {

    private static final String basicSearchUrl =
            "https://lib.goe.go.kr/elib/module/elib/search/index.do?com_code=&menu_idx=94&viewPage=1&type=&search_text=";

    private static final String MORE_VIEW_COUNT ="&rowCount=";


    public static String basicSearchUrlCreate(String searchKeyword) {
        return new StringBuilder(basicSearchUrl).append(searchKeyword)
                .toString();
    }

    public static String moreViewSearchUrlCreate(String basicUrl,int totalCount) {
        return new StringBuilder().append(basicUrl)
                .append(MORE_VIEW_COUNT).append(totalCount)
                .toString();
    }



}
