package io.dodn.springboot.core.api.controller.v1;

import io.dodn.springboot.core.api.domain.response.LibraryServiceResponse;
import io.dodn.springboot.core.api.service.LibraryService;
import io.dodn.springboot.core.api.support.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    private final LibraryService libraryService;

    //그냥 응답값을 대출 가능여부를 보내기만 하자


    @GetMapping("/api/v1/gyeonggiDoCyberLibrarySearch/{searchKeyword}")
    public ApiResponse<?> gyeonggiDoCyberLibrarySearch(
                                 @PathVariable String searchKeyword) {
        validation(searchKeyword);
        LibraryServiceResponse libraryServiceResponse = libraryService.gyeonggiDoCyberLibrarySearch(searchKeyword);
        return ApiResponse.success(libraryServiceResponse);
    }


    @GetMapping("/api/v1/gyeonggiEducationalElectronicLibrary/{searchKeyword}")
    public ApiResponse<?> gyeonggiEducationalElectronicLibrarySearch(
                                 @PathVariable String searchKeyword) {
        validation(searchKeyword);
        return ApiResponse.success(libraryService.gyeonggiEducationalElectronicLibrarySearch(searchKeyword));
    }


    @GetMapping("/api/v1/smallBusinessLibrary/{searchKeyword}")
    public ApiResponse<?> smallBusinessLibrarySearch(
                                 @PathVariable String searchKeyword) {
        validation(searchKeyword);
        return ApiResponse.success(libraryService.smallBusinessLibrarySearch(searchKeyword));
    }

    private void validation(String searchKeyword) {

        System.out.println("요청옴 = " + searchKeyword);
        if(searchKeyword.isBlank()) {
            throw new IllegalArgumentException(searchKeyword + " 빈 문자열입니다.");
        }
    }


}
