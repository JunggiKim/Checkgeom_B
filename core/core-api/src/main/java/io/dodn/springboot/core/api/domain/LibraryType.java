package io.dodn.springboot.core.api.domain;

public enum LibraryType {

    ALL ("all"),
    GYEONGGIDO_CYBER ("gyeonggiDoCyberLibrary"),
    GYEONGGI_EDUCATIONAL_ELECTRONIC("gyeonggiEducationalElectronic"),
    SMALL_BUSINESS("smallBusiness");

    private final String text ;

    LibraryType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
