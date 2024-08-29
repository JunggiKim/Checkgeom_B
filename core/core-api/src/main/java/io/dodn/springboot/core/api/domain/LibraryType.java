package io.dodn.springboot.core.api.domain;


public enum LibraryType {

    ALL ("all","전체"),
    GYEONGGIDO_CYBER ("gyeonggiDoCyberLibrary","경기도사이버도서관"),
    GYEONGGI_EDUCATIONAL_ELECTRONIC("gyeonggiEducationalElectronic","경기교육전자도서관"),
    SMALL_BUSINESS("smallBusiness","소상공인전자도서관");

    public String getEnglishText() {
        return englishText;
    }

    public String getKoreanText() {
        return koreanText;
    }

    private final String englishText;
    private final String koreanText;


    LibraryType(String englishText, String koreanText) {
		this.englishText = englishText;
		this.koreanText = koreanText;
    }


}
