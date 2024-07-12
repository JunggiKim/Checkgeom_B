package io.dodn.springboot.core.api.controller.v1;

import io.dodn.springboot.core.api.domain.GyeonggiDoCyberLibraryService;
import io.dodn.springboot.core.api.support.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GyeonggiDoCyberLibraryController {

    public GyeonggiDoCyberLibraryController(GyeonggiDoCyberLibraryService gyeonggiDoCyberLibraryService) {
        this.gyeonggiDoCyberLibraryService = gyeonggiDoCyberLibraryService;
    }

    private final GyeonggiDoCyberLibraryService gyeonggiDoCyberLibraryService;

    @GetMapping("/get/data")
    public ApiResponse<?> testLibraryAPI() {
        String url = "https://ebook.library.kr/search?keyword=처음&searchType=all&listType=list&sort=publishDate";

        return ApiResponse.success(gyeonggiDoCyberLibraryService.search(url));
    }

}
