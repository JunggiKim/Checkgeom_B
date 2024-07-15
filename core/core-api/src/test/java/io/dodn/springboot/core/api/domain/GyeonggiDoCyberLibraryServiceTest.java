package io.dodn.springboot.core.api.domain;

import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryReader;
import io.dodn.springboot.core.api.domain.request.SearchServiceRequest;
import io.dodn.springboot.core.api.domain.response.GyeonggiDoCyberLibraryServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("test")
class GyeonggiDoCyberLibraryServiceTest {

    private static final Logger log = LoggerFactory.getLogger(GyeonggiDoCyberLibraryServiceTest.class);
    @Autowired
    LibraryService libraryService;

    @Autowired
    GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader;

    @DisplayName("검색한 책을 가져온다")
    @Test
    void search() throws Exception {
        // given
        SearchServiceRequest request = new SearchServiceRequest(
           "처음",
           "all",
           "list",
           "publishDate"
        );
        // when

        GyeonggiDoCyberLibraryServiceResponse result = libraryService.search(request);
        // List<검색한 책> 검색한 책 = gyeonggiDoCyberLibraryService.search();

        // then

        log.info("result = {}" , result);

        // 책에 대한 정보 들을 검증한다.
        // 제목 , 책이미지 링크 , 저자 , 출판사
        // 출판 날짜, 대출 예약 현황

    }

}
