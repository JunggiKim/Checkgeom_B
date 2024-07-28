package io.dodn.springboot.core.api.service;

import io.dodn.springboot.core.api.config.IntegrationTest;
import io.dodn.springboot.core.api.domain.LibraryType;
import io.dodn.springboot.core.api.service.response.AllLibraryServiceResponse;
import io.dodn.springboot.core.api.service.response.LibrarySearchServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class LibraryServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader;


    @DisplayName("경기도 사이버도서관 에서 검색한 결과를 반환 받는다.")
    @Test
    void gyeonggiDoCyberLibrarySearch() throws Exception {
        // given
        String searchKeyword = "객체";

        // when
        LibrarySearchServiceResponse librarySearchServiceResponse = libraryService.gyeonggiDoCyberLibrarySearch(searchKeyword);

        // then0

        boolean keywordMatch = librarySearchServiceResponse.bookDtoList().stream()
                .allMatch(bookDto -> bookDto.title().replaceAll(" ", "").toLowerCase().contains(searchKeyword.toLowerCase()));


//        assertThat(librarySearchServiceResponse.moreViewLink()).isNotEmpty();

        assertThat(librarySearchServiceResponse.bookDtoList()).isNotEmpty();
        assertThat(keywordMatch).isTrue();
        assertThat(librarySearchServiceResponse.bookSearchTotalCount()).isNotZero();
        assertThat(librarySearchServiceResponse.libraryTypeText()).isEqualTo(LibraryType.GYEONGGIDO_CYBER.getText());

    }


    @DisplayName("경기교육 전자 도서관에서 검색을 한다.")
    @Test
    void gyeonggiEducationalElectronicLibrarySearch() throws Exception {

        //given
        String searchKeyword = "객체";

        //when
        LibrarySearchServiceResponse librarySearchServiceResponse = libraryService.gyeonggiEducationalElectronicLibrarySearch(searchKeyword);

        //then
        boolean keywordMatch = librarySearchServiceResponse.bookDtoList().stream()
                .allMatch(bookDto -> bookDto.title().replaceAll(" ", "").toLowerCase().contains(searchKeyword.toLowerCase()));

        assertThat(librarySearchServiceResponse.bookDtoList()).isNotEmpty();
        assertThat(keywordMatch).isTrue();
        assertThat(librarySearchServiceResponse.bookSearchTotalCount()).isNotZero();
        assertThat(librarySearchServiceResponse.libraryTypeText()).isEqualTo(LibraryType.GYEONGGI_EDUCATIONAL_ELECTRONIC.getText());


    }

    @DisplayName("소상 공인 전자 도서관에서 검색을 한다.")
    @Test
    void smallBusinessLibrarySearch() throws Exception {
        //given
        String searchKeyword = "객체";

        //when
        LibrarySearchServiceResponse librarySearchServiceResponse = libraryService.smallBusinessLibrarySearch(searchKeyword);

        //then
        boolean keywordMatch = librarySearchServiceResponse.bookDtoList().stream()
                .allMatch(bookDto -> bookDto.title().replaceAll(" ", "").toLowerCase().contains(searchKeyword.toLowerCase()));


        assertThat(librarySearchServiceResponse.bookDtoList()).isNotEmpty();
        assertThat(keywordMatch).isTrue();
        assertThat(librarySearchServiceResponse.bookSearchTotalCount()).isNotZero();
        assertThat(librarySearchServiceResponse.libraryTypeText()).isEqualTo(LibraryType.SMALL_BUSINESS.getText());

    }


    @DisplayName("모든 도서관에서 전체 검색을 해서 값을 가져온다.")
    @Test
    void allLibrarySearch() throws Exception {
        //given
        String searchKeyword = "객체";

        //when
        AllLibraryServiceResponse response = libraryService.allLibrarySearch(searchKeyword);

        //then


        boolean keywordMatch = true;
        boolean booksNotEmpty = true;
        boolean bookSearchTotalCountNotZero = true;

        for (LibrarySearchServiceResponse librarySearchServiceResponse : response.librarySearchServiceResponseList()) {
            for (LibrarySearchServiceResponse.BookDto bookDto : librarySearchServiceResponse.bookDtoList()) {
                if (!bookDto.title().replaceAll(" ", "").toLowerCase().contains(searchKeyword.toLowerCase())) {
                    keywordMatch = false;
                }
            }
            if (librarySearchServiceResponse.bookDtoList().isEmpty()) {
                booksNotEmpty = false;
            }
            if (librarySearchServiceResponse.bookSearchTotalCount() == 0) {
                bookSearchTotalCountNotZero = false;
            }

        }


        assertThat(booksNotEmpty).isTrue();
        assertThat(keywordMatch).isTrue();
        assertThat(bookSearchTotalCountNotZero).isTrue();
        assertThat(response.libraryTypeText()).isEqualTo(LibraryType.ALL.getText());
    }


    @DisplayName("모든 도서관에서 비동기로 전체 검색을 해서 값을 가져온다.")
    @Test
    void allLibraryAsyncSearch() throws Exception {
        //given
        String searchKeyword = "객체";

        //when
        AllLibraryServiceResponse response = libraryService.allLibraryAsyncSearch(searchKeyword);

        //then


        boolean keywordMatch = true;
        boolean booksNotEmpty = true;
        boolean bookSearchTotalCountNotZero = true;

        for (LibrarySearchServiceResponse librarySearchServiceResponse : response.librarySearchServiceResponseList()) {
            for (LibrarySearchServiceResponse.BookDto bookDto : librarySearchServiceResponse.bookDtoList()) {
                if (!bookDto.title().replaceAll(" ", "").toLowerCase().contains(searchKeyword.toLowerCase())) {
                    keywordMatch = false;
                }
            }
            if (librarySearchServiceResponse.bookDtoList().isEmpty()) {
                booksNotEmpty = false;
            }
            if (librarySearchServiceResponse.bookSearchTotalCount() == 0) {
                bookSearchTotalCountNotZero = false;
            }

        }


        assertThat(booksNotEmpty).isTrue();
        assertThat(keywordMatch).isTrue();
        assertThat(bookSearchTotalCountNotZero).isTrue();
        assertThat(response.libraryTypeText()).isEqualTo(LibraryType.ALL.getText());
    }


}
