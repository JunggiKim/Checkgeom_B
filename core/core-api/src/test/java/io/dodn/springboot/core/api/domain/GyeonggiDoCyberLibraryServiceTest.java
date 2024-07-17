package io.dodn.springboot.core.api.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dodn.springboot.core.api.config.IntegrationTest;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.ATOMIC_STAMPED_REFERENCE;

@SpringBootTest
@Tag("test")
class GyeonggiDoCyberLibraryServiceTest extends IntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(GyeonggiDoCyberLibraryServiceTest.class);
    @Autowired
    private LibraryService libraryService;

    private MockMvc mvc;

    private ObjectMapper objectMapper;


    @Autowired
    private GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader;

    @DisplayName("경기도 사이버도서관 에서 검색한 결과를 반환 받는다.")
    @Test
    void gyeonggiDoCyberLibrarySearch() throws Exception {
        // given
        String searchKeyword = "처음";

        // when
        LibraryServiceResponse libraryServiceResponse = libraryService.gyeonggiDoCyberLibrarySearch(searchKeyword);

        // then
        boolean keywordMatch = libraryServiceResponse.bookDtoList().stream()
                .allMatch(bookDto -> bookDto.title().contains(searchKeyword));
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
                .allMatch(bookDto -> bookDto.title().contains(searchKeyword));
        assertThat(keywordMatch).isTrue();

    }

    @DisplayName("경기교육전자도서관에서 검색을 한다.")
    @Test
    void smallBusinessLibrarySearch() throws Exception {
        //given
        String searchKeyword = "처음";

        //when
        LibraryServiceResponse libraryServiceResponse = libraryService.smallBusinessLibrarySearch(searchKeyword);

        //then
        boolean keywordMatch = libraryServiceResponse.bookDtoList().stream()
                .allMatch(bookDto -> bookDto.title().contains(searchKeyword));
        assertThat(keywordMatch).isTrue();
    }


}
