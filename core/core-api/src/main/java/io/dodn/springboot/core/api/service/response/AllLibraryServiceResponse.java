package io.dodn.springboot.core.api.service.response;

import io.dodn.springboot.storage.db.core.response.LibraryRepositoryResponse;

import java.util.List;

public record AllLibraryServiceResponse(List<LibrarySearchServiceResponse> librarySearchServiceResponseList, String libraryTypeText) {

    public static AllLibraryServiceResponse of(List<LibrarySearchServiceResponse> librarySearchServiceResponseList, String libraryTypeText) {
        return new AllLibraryServiceResponse(librarySearchServiceResponseList, libraryTypeText);
    }

    public record BookDto(String bookImageLink, String title, String author, String publisher, String publicationDate,
                          String loanAvailability) {

        public static AllLibraryServiceResponse.BookDto of(
                LibraryRepositoryResponse repositoryResponse) {

            return new AllLibraryServiceResponse.BookDto(repositoryResponse.bookImageLink(),
                    repositoryResponse.title(), repositoryResponse.author(), repositoryResponse.publisher(),
                    repositoryResponse.publicationDate(), repositoryResponse.loanAvailability());
        }

        public static AllLibraryServiceResponse.BookDto of(
                String bookImageLink,
                String title,
                String author,
                String publisher,
                String publicationDate,
                String loanAvailability
        ) {
            return new AllLibraryServiceResponse.BookDto(
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