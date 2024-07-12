package io.dodn.springboot.core.api.controller.v1.request;

public record TestRequestDto(
    String libraryType,
    String searchType,
    String searchText
) {
}
