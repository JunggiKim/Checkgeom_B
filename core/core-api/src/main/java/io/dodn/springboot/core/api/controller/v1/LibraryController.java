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

    //그냥 응답값을 대출 가능여부를 보내기만 하자


    @GetMapping("/api/v1/gyeonggiDoCyberLibrarySearch/search")
    public ApiResponse<?> gyeonggiDoCyberLibrarySearch(
                                 @RequestParam String searchKeyword) {
        validation(searchKeyword);
        return ApiResponse.success(libraryService.gyeonggiDoCyberLibrarySearch(searchKeyword));
    }


    @GetMapping("/api/v1/gyeonggiEducationalElectronicLibrary/search")
    public ApiResponse<?> gyeonggiEducationalElectronicLibrarySearch(
                                 @RequestParam String searchKeyword) {
        validation(searchKeyword);
        return ApiResponse.success(libraryService.gyeonggiEducationalElectronicLibrarySearch(searchKeyword));
    }


    @GetMapping("/api/v1/smallBusinessLibrary/search")
    public ApiResponse<?> smallBusinessLibrarySearch(
                                 @RequestParam String searchKeyword) {
        validation(searchKeyword);
        return ApiResponse.success(libraryService.smallBusinessLibrarySearch(searchKeyword));
    }

    private void validation(String searchKeyword) {
        if(searchKeyword.isBlank()) {
            throw new IllegalArgumentException("빈 문자열입니다.");
        }
    }


}
