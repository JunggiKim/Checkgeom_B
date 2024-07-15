package io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary;

import java.util.Arrays;

public enum GyeonggiDoCyberLibraryBookType {

    COLLECTION("소장", "EB"),
    SUBSCRIPTION("구독", "SUBS");

    private final String name;

    private final String urlType;

    GyeonggiDoCyberLibraryBookType(String name, String urlType) {
        this.name = name;
        this.urlType = urlType;
    }

    public static GyeonggiDoCyberLibraryBookType of(String bookType) {
    return   Arrays.stream(GyeonggiDoCyberLibraryBookType.values())
                .filter(enumBookType -> enumBookType.name.contains(bookType))
                .findFirst().orElseThrow(() -> new RuntimeException(bookType + "찾는 Url 값이 없습니다."));
    };

    public String getUrlType() {
        return urlType;
    }

    public String getName() {
        return name;
    }

}
