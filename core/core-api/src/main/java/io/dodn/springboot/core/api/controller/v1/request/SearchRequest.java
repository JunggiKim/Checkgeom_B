package io.dodn.springboot.core.api.controller.v1.request;

import io.dodn.springboot.core.api.service.request.SearchServiceRequest;

public record SearchRequest(String keyword, String searchType, String listType, String sort) {

    public static SearchRequest of(String keyword, String searchType, String listType, String sort) {
        return new SearchRequest(keyword, searchType, listType, sort);
    }

    public SearchServiceRequest toServiceRequest() {
        return new SearchServiceRequest(
                this.keyword,
                this.searchType,
                this.listType,
                this.sort
        );

    }
}
