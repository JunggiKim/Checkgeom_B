package io.dodn.springboot.core.api.domain.response;

import io.dodn.springboot.storage.db.core.response.GyeonggiDoCyberLibraryRepositoryResponse;

import java.util.List;

public record GyeonggiDoCyberLibraryServiceResponse(List<BookDto> bookDtoList, int totalCount ,List<String > moreViewLink) {

    public static GyeonggiDoCyberLibraryServiceResponse of(List<BookDto> bookDtoList, int totalCount ,List<String > moreViewLink ) {
        return new GyeonggiDoCyberLibraryServiceResponse(bookDtoList, totalCount , moreViewLink);
    }

    public record BookDto(String bookImageLink, String title, String author, String publisher, String publicationDate,
            String loanAvailability) {
        public static GyeonggiDoCyberLibraryServiceResponse.BookDto of(
                GyeonggiDoCyberLibraryRepositoryResponse repositoryResponse) {
            return new GyeonggiDoCyberLibraryServiceResponse.BookDto(repositoryResponse.bookImageLink(),
                    repositoryResponse.title(), repositoryResponse.author(), repositoryResponse.publisher(),
                    repositoryResponse.publicationDate(), repositoryResponse.loanAvailability());
        }
    }

}
