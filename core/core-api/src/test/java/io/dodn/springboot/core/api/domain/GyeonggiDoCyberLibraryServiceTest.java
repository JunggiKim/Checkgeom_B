package io.dodn.springboot.core.api.domain;

import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryReader;
import io.dodn.springboot.core.api.domain.request.SearchServiceRequest;
import io.dodn.springboot.core.api.domain.response.LibraryServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
    void gyeonggiDoCyberLibrarySearch() throws Exception {
        // given
        SearchServiceRequest request = new SearchServiceRequest(
           "처음",
           "all",
           "list",
           "publishDate"
        );
        // when

        LibraryServiceResponse result = libraryService.gyeonggiDoCyberLibrarySearch(request);
        // List<검색한 책> 검색한 책 = gyeonggiDoCyberLibraryService.gyeonggiDoCyberLibrarySearch();

        // then

        log.info("result = {}" , result);


    }

            @DisplayName("경기교육전자도서관에서 검색을 한다.")
            @Test
            void gyeonggiEducationalElectronicLibrarySearch() throws Exception {
                //given

                //when
                LibraryServiceResponse libraryServiceResponse = libraryService.gyeonggiEducationalElectronicLibrarySearch("처음");

                //then

                System.out.println(libraryServiceResponse);

            }

            @DisplayName("경기교육전자도서관에서 검색을 한다.")
            @Test
            void smallBusinessLibrarySearch() throws Exception {
                //given

                //when
                Object libraryServiceResponse = libraryService.smallBusinessLibrarySearch("처음");

                //then

                System.out.println(libraryServiceResponse);

            }


}
