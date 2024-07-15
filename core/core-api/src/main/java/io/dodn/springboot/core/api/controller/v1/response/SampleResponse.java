package io.dodn.springboot.core.api.controller.v1.response;

import java.time.LocalDateTime;
import java.util.List;

public record SampleResponse(List<BookDto> bookDtos, Long totalSearchCount) {
    record BookDto(String Author, String publisher, String title, LocalDateTime publicationDate, String bookLink,
            int currentNumberOfLoans, int maximumNumberOfLoans, int maximumNumberOfReservations,
            int currentNumberOfReservations) {

    }

}
