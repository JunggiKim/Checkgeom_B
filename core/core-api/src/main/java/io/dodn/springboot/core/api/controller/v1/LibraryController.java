package io.dodn.springboot.core.api.controller.v1;

import io.dodn.springboot.core.api.controller.v1.request.SearchRequest;
import io.dodn.springboot.core.api.domain.LibraryService;
import io.dodn.springboot.core.api.support.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class LibraryController {

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    private final LibraryService libraryService;

    @GetMapping("/api/v1/gyeonggiDoCyberLibrarySearch/search")
    public ApiResponse<?> gyeonggiDoCyberLibrarySearch(@RequestParam("keyword") String keyword,
                                 @RequestParam("searchType") String searchType,
                                 @RequestParam("listType") String listType,
                                 @RequestParam("sort") String sort) {

        SearchRequest searchRequest = SearchRequest.of(keyword, searchType, listType, sort);
        return ApiResponse.success(libraryService.gyeonggiDoCyberLibrarySearch(searchRequest.toServiceRequest()));
    }

    @GetMapping("/api/v1/gyeonggiEducationalElectronicLibrary/search")
    public ApiResponse<?> gyeonggiEducationalElectronicLibrarySearch(
                                 @RequestParam("keyword") String keyword,
                                 @RequestParam("searchType") String searchType,
                                 @RequestParam("listType") String listType,
                                 @RequestParam("sort") String sort) {

        SearchRequest searchRequest = SearchRequest.of(keyword, searchType, listType, sort);
        return ApiResponse.success(libraryService.gyeonggiDoCyberLibrarySearch(searchRequest.toServiceRequest()));
    }

}
