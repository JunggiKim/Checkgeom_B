package io.dodn.springboot.core.api.domain;

import java.time.LocalDateTime;

public class GyeonggiDoCyberLibraryBook {

    private String author;

    private String publisher;

    private String title;

    private LocalDateTime publicationDate;

    private Boolean loanAvailability;

    private String bookImageLink;

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public Boolean getLoanAvailability() {
        return loanAvailability;
    }

    public String getBookImageLink() {
        return bookImageLink;
    }

}
