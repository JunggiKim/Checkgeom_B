package io.dodn.springboot.core.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dodn.springboot.core.api.config.MockTest;
import io.dodn.springboot.core.api.service.LibraryService;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryReader;
import io.dodn.springboot.core.api.domain.response.LibraryServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = LibraryController.class)
class LibraryControllerTest extends MockTest {


    @MockBean
    private LibraryService libraryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader;

    @DisplayName("경기도 사이버 도서관으로 검색을 하면 올바른 도서검색값이 나온다.")
    @Test
    void gyeonggiDoCyberLibrarySearch() throws Exception {

        String keyword = UUID.randomUUID().toString();

//        given(libraryService.gyeonggiDoCyberLibrarySearch(anyString()))
//                .willReturn(LibraryServiceResponse.of());

        //given
//        mockMvc.perform(get("/api/v1/gyeonggiDoCyberLibrarySearch/"+keyword)
//                .contentType()
//
//        )



        //when

        //then
    }



}