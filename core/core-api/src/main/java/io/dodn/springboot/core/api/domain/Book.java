package io.dodn.springboot.core.api.domain;

import io.dodn.springboot.storage.db.core.response.LibraryRepositoryResponse;

public class Book {

     private final String bookImageLink;
     private final String title;
     private final String author;
     private final String publisher;
     private final String publicationDate;
     private final String loanAvailability;



    private Book(String bookImageLink, String title, String author, String publisher, String publicationDate, String loanAvailability, String bookImageLink1, String title1, String author1, String publisher1, String publicationDate1, String loanAvailability1) {
        this.bookImageLink = bookImageLink1;
        this.title = title1;
        this.author = author1;
        this.publisher = publisher1;
        this.publicationDate = publicationDate1;
        this.loanAvailability = loanAvailability1;
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
                loanAvailability,
                , , , , , );
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
