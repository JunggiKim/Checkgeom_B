package io.dodn.springboot.core.api.controller.v1;

import io.dodn.springboot.core.api.domain.SearchType;
import io.dodn.springboot.core.api.service.LibrarySearchService;
import io.dodn.springboot.core.api.service.response.LibrarySearchServiceResponse;
import io.dodn.springboot.core.api.support.response.ApiResponse;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class LibraryController {

    public LibraryController(LibrarySearchService librarySearchService) {
        this.libraryService = librarySearchService;
    }

    private final LibrarySearchService libraryService;

    //그냥 응답값을 대출 가능여부를 보내기만 하자


    @GetMapping("/api/v1/gyeonggiDoCyberLibrary")
    public ApiResponse<?> gyeonggiDoCyberLibrarySearch(
        @RequestParam @NotBlank String searchKeyword
    ) {
        LibrarySearchServiceResponse librarySearchServiceResponse = libraryService.gyeonggiDoCyberLibrarySearch(searchKeyword );
        return ApiResponse.success(librarySearchServiceResponse);
    }


    @GetMapping("/api/v1/gyeonggiEducationalElectronic")
    public ApiResponse<?> gyeonggiEducationalElectronicLibrarySearch(
                @RequestParam @NotBlank String searchKeyword
    ) {
        return ApiResponse.success(libraryService.gyeonggiEducationalElectronicLibrarySearch(searchKeyword ));
    }


    @GetMapping("/api/v1/smallBusiness/{searchKeyword}")
    public ApiResponse<?> smallBusinessLibrarySearch(
        @RequestParam @NotBlank String searchKeyword

    ) {
        return ApiResponse.success(libraryService.smallBusinessLibrarySearch(searchKeyword ));
    }

    @GetMapping("/api/v1/allLibrary/{searchKeyword}")
    public ApiResponse<?> allLibraryAsyncSearch(@PathVariable("searchKeyword") @NotBlank String searchKeyword) {
        return ApiResponse.success(libraryService.allLibraryAsyncSearch(searchKeyword ));
    }

    @GetMapping("/api/v1/ok")
    public ApiResponse<?> check() {
        return ApiResponse.success();
    }


    // @GetMapping("/api/v1/allLibrary2")
    // public ApiResponse<?> allLibraryAsyncSearch(
    //     @RequestParam @NotBlank String searchKeyword,
    //     @RequestParam  SearchType searchType
    // ) {
    //     return ApiResponse.success(libraryService.allLibraryAsyncSearch(searchKeyword ));
    // }

    // @GetMapping("/api/v1/allLibraryVirtualThreadAsyncSearch/{searchKeyword}")
    // public ApiResponse<?> allLibraryVirtualThreadAsyncSearch(@PathVariable String searchKeyword) throws ExecutionException, InterruptedException {
    //     validation(searchKeyword);
    //     return ApiResponse.success(libraryService.allLibraryVirtualThreadAsyncSearch(searchKeyword));
    // }


}
