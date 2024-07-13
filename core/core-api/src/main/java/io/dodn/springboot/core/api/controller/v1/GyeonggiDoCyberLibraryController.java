package io.dodn.springboot.core.api.controller.v1;

import io.dodn.springboot.core.api.controller.v1.request.SearchRequest;
import io.dodn.springboot.core.api.domain.GyeonggiDoCyberLibraryService;
import io.dodn.springboot.core.api.support.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GyeonggiDoCyberLibraryController {

    public GyeonggiDoCyberLibraryController(GyeonggiDoCyberLibraryService gyeonggiDoCyberLibraryService) {
        this.gyeonggiDoCyberLibraryService = gyeonggiDoCyberLibraryService;
    }

    private final GyeonggiDoCyberLibraryService gyeonggiDoCyberLibraryService;

    @GetMapping("/api/v1/search")
    public ApiResponse<?> search(
            @RequestParam("keyword") String keyword,
            @RequestParam("searchType") String searchType,
            @RequestParam("listType") String listType,
            @RequestParam("sort") String sort
            ) {
        SearchRequest searchRequest = SearchRequest.of(keyword, searchType, listType, sort);

        return ApiResponse.success(gyeonggiDoCyberLibraryService.search(searchRequest));
    }

}
