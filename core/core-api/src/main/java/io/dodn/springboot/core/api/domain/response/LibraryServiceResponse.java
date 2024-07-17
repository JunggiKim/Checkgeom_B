package io.dodn.springboot.core.api.domain.response;

import io.dodn.springboot.storage.db.core.response.LibraryRepositoryResponse;

import java.util.List;

public record LibraryServiceResponse(List<BookDto> bookDtoList, int totalCount , List<String > moreViewLink) {

    public static LibraryServiceResponse of(List<BookDto> bookDtoList, int totalCount , List<String > moreViewLink ) {
        return new LibraryServiceResponse(bookDtoList, totalCount , moreViewLink);
    }

    public record BookDto(String bookImageLink, String title, String author, String publisher, String publicationDate,
            String loanAvailability) {

        public static LibraryServiceResponse.BookDto of(
                LibraryRepositoryResponse repositoryResponse) {

            return new LibraryServiceResponse.BookDto(repositoryResponse.bookImageLink(),
                    repositoryResponse.title(), repositoryResponse.author(), repositoryResponse.publisher(),
                    repositoryResponse.publicationDate(), repositoryResponse.loanAvailability());
        }

        public static LibraryServiceResponse.BookDto of(
                String bookImageLink,
                String title,
                String author,
                String publisher,
                String publicationDate,
                String loanAvailability
        ) {
            return new LibraryServiceResponse.BookDto(
                     bookImageLink,
                     title,
                     author,
                     publisher,
                     publicationDate,
                     loanAvailability
            );
        }



        @Override
        public String toString() {
            return "BookDto{" +
                    "bookImageLink='" + bookImageLink + '\'' +
                    ", title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", publisher='" + publisher + '\'' +
                    ", publicationDate='" + publicationDate + '\'' +
                    ", loanAvailability='" + loanAvailability + '\'' +
                    '}';
        }


    }

}
