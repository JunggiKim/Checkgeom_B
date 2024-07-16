package io.dodn.springboot.core.api.domain.gyeonggiEducationalElectronicLibrary;

import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryUrlEnum;

public class gyeonggiEducationalElectronicLibrary {


    public static final String basicSearchUrl =
            "https://lib.goe.go.kr/elib/module/elib/search/index.do?com_code=";



    public static String basicSearchUrlCreate(String keyword) {
        return new StringBuilder().append(basicSearchUrl)
                .append("&menu_idx=").append("94")
                .append("&viewPage=").append("1")
                .append("&type=")
                .append("&search_text=").append(keyword)
                .toString();
    }



}
