package io.dodn.springboot.core.api.config;

public enum TestProfile {

    TEST("test"),
    DEV("dev");


    TestProfile(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    private final String text;
}
