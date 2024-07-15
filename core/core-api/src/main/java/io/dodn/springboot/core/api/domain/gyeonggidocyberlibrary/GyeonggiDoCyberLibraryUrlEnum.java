package io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary;

public enum GyeonggiDoCyberLibraryUrlEnum {

    keyword("keyword="),
    searchType("&searchType="),
    listType("&listType="),
    sort("&sort="),
    contentType("&contentType="),
    asc("&asc="),
    size("&size=");

    private final String text;

    GyeonggiDoCyberLibraryUrlEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
