package io.dodn.springboot.core.api.domain;

public class GyeonggiDoCyberLibrary {

    private static final String basicSearchURL = "https://ebook.library.kr/search?";

    private GyeonggiDoCyberLibraryBookType gyeonggiDoCyberLibraryBookType;


    public static String basicSearchUrlCreate(
            String keyword,
            String searchType,
            String listType,
            String sort
    ) {
        StringBuilder sb = new StringBuilder();

        return    sb.append(basicSearchURL)
                .append("keyword=").append(keyword)
                .append("&searchType=").append(searchType)
                .append("&listType=").append(listType)
                .append("&sort==").append(sort)
                .toString();

}


    public static String seeMoreSearchUrlCreate(String searchType
            , String searchText
            , GyeonggiDoCyberLibraryBookType gyeonggiDoCyberLibraryBookType
            , String searchSize
    ) {
        return basicSearchURL +
                "keyword=" + searchText +
                "&searchType=" + searchType +
                "&listType=" + "list" +
                "&sort=" + "publishDate" +
                "&contentType=" + gyeonggiDoCyberLibraryBookType.getType()+
                "&asc=" + "desc"+
                "&size=" + searchSize;

    }




}
