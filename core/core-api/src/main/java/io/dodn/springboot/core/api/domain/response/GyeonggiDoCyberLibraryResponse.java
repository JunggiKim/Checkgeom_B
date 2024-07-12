package io.dodn.springboot.core.api.domain.response;

import java.time.LocalDateTime;

public record GyeonggiDoCyberLibraryResponse(
        String bookImageLink,
        String author ,
        String publisher,
        String title,
        String publicationDate,
        String loanAvailability
) {

    public static GyeonggiDoCyberLibraryResponse of (
            String bookImageLink,
            String title,
            String author ,
            String publisher,
            String publicationDate,
            String loanAvailability
    ) {
        return new GyeonggiDoCyberLibraryResponse(
                 bookImageLink,
                 title,
                 author ,
                 publisher,
                 publicationDate,
                 loanAvailability
        );
    }

}
