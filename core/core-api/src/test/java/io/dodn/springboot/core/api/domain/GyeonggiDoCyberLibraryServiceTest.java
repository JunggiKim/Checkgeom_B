package io.dodn.springboot.core.api.domain;

import io.dodn.springboot.core.api.domain.response.GyeonggiDoCyberLibraryResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GyeonggiDoCyberLibraryServiceTest {


    @Mock
    GyeonggiDoCyberLibraryService gyeonggiDoCyberLibraryService;


    @DisplayName("검색한 책을 가져온다")
    @Test
    void search() throws Exception {
        //given

        샘플 책 데이터 = 샘플 책 데이터 추가()
        //when
        List<책데이터> 책데이터 = gyeonggiDoCyberLibraryService.search();
        //then
        assertThat().isEqualTo();

//        책에 대한 정보 들을 검증한다.
//         제목 , 책이미지 링크 , 저자 , 출판사
//        출판 날짜, 대출  예약 현황

    }

}

