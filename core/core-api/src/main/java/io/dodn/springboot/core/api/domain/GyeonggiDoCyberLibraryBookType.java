package io.dodn.springboot.core.api.domain;


public enum GyeonggiDoCyberLibraryBookType {

    COLLECTION("소장", "EB"),
    SUBSCRIPTION("구독", "SUBS");

    private final String name;
    private final String type;

    GyeonggiDoCyberLibraryBookType(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
