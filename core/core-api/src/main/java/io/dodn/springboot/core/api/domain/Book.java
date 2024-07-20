package io.dodn.springboot.core.api.domain;

import io.dodn.springboot.storage.db.core.response.LibraryRepositoryResponse;

public class Book {

    private String bookImageLink;
    private String title;
    private String author;
    private String publisher;
    private String publicationDate;
    private String loanAvailability;

    private Book(String bookImageLink, String title, String author, String publisher, String publicationDate, String loanAvailability) {
    }

    public static Book of(
            LibraryRepositoryResponse repositoryResponse) {

        return new Book(repositoryResponse.bookImageLink(),
                repositoryResponse.title(), repositoryResponse.author(), repositoryResponse.publisher(),
                repositoryResponse.publicationDate(), repositoryResponse.loanAvailability());
    }

    public static Book of(
            String bookImageLink,
            String title,
            String author,
            String publisher,
            String publicationDate,
            String loanAvailability
    ) {
        return new Book(
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
