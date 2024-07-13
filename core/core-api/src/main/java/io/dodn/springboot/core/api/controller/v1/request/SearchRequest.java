package io.dodn.springboot.core.api.controller.v1.request;

public record SearchRequest(
        String keyword,
        String searchType,
        String listType,
        String sort
) {


    public static SearchRequest of(
            String keyword,
            String searchType,
            String listType,
            String sort
    ) {

        return new SearchRequest(
                keyword,
                searchType,
                listType,
                sort
        );
    }


}
