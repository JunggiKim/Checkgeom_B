package io.dodn.springboot.core.api.domain;

public class GyeonggiDoCyberLibrary {

    private final String basicSearchURL = "https://ebook.library.kr/search?";

    private GyeonggiDoCyberLibraryBookType gyeonggiDoCyberLibraryBookType;


    public String search(String searchType, String searchText) {
        return basicSearchURL +
                "keyword=" + searchText +
                "&searchType=" + searchType +
                "&listType=" + "list" +
                "&sort=" + "publishDate";
    }


    public String seeMoreSearch(String searchType
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
