package io.dodn.springboot.core.api.domain;

import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryReader;
import io.dodn.springboot.core.api.service.response.LibraryServiceResponse;
import io.dodn.springboot.core.api.service.LibraryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("test")
class GyeonggiDoCyberLibraryServiceTest{

    private static final Logger log = LoggerFactory.getLogger(GyeonggiDoCyberLibraryServiceTest.class);

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader;




    @DisplayName("경기도 사이버도서관 에서 검색한 결과를 반환 받는다.")
    @Test
    void gyeonggiDoCyberLibrarySearch() throws Exception {
        // given
        String searchKeyword = "처음";

        // when
        LibraryServiceResponse libraryServiceResponse = libraryService.gyeonggiDoCyberLibrarySearch(searchKeyword);

        // then0

        boolean keywordMatch = libraryServiceResponse.bookDtoList().stream()
                .allMatch(bookDto ->bookDto.title().replaceAll(" ","").toLowerCase().contains(searchKeyword.toLowerCase()));
        assertThat(keywordMatch).isTrue();

    }

    @DisplayName("경기교육 전자 도서관에서 검색을 한다.")
    @Test
    void gyeonggiEducationalElectronicLibrarySearch() throws Exception {

        //given
        String searchKeyword = "처음";

        //when
        LibraryServiceResponse libraryServiceResponse = libraryService.gyeonggiEducationalElectronicLibrarySearch(searchKeyword);

        //then
        boolean keywordMatch = libraryServiceResponse.bookDtoList().stream()
                .allMatch(bookDto ->bookDto.title().replaceAll(" ","").toLowerCase().contains(searchKeyword.toLowerCase()));
        assertThat(keywordMatch).isTrue();

    }

    @DisplayName("소상 공인 전자 도서관에서 검색을 한다.")
    @Test
    void smallBusinessLibrarySearch() throws Exception {
        //given
        String searchKeyword = "처음";

        //when
        LibraryServiceResponse libraryServiceResponse = libraryService.smallBusinessLibrarySearch(searchKeyword);

        //then
        boolean keywordMatch = libraryServiceResponse.bookDtoList().stream()
                .allMatch(bookDto ->bookDto.title().replaceAll(" ","").toLowerCase().contains(searchKeyword.toLowerCase()));

        assertThat(keywordMatch).isTrue();
    }


}
