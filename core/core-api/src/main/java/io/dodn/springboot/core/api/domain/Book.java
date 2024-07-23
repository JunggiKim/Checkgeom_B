package io.dodn.springboot.core.api.domain;

import io.dodn.springboot.storage.db.core.response.LibraryRepositoryResponse;

public class Book {

     private final String bookImageLink;
     private final String title;
     private final String author;
     private final String publisher;
     private final String publicationDate;
     private final String loanAvailability;



    private Book(String bookImageLink, String title, String author, String publisher, String publicationDate, String loanAvailability) {
        this.bookImageLink = bookImageLink;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.loanAvailability = loanAvailability;
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
                loanAvailability);
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
