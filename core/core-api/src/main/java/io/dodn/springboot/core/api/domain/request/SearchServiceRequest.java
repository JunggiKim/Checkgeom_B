package io.dodn.springboot.core.api.domain.request;

import io.dodn.springboot.core.api.controller.v1.request.SearchRequest;

public record SearchServiceRequest (
        String keyword,
        String searchType,
        String listType,
        String sort
){

}
