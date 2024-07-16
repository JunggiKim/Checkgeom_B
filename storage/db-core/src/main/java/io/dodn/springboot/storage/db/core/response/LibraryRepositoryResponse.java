package io.dodn.springboot.storage.db.core.response;

public record LibraryRepositoryResponse(String bookImageLink, String title, String author,
                                        String publisher, String publicationDate, String loanAvailability) {

    public static LibraryRepositoryResponse of(String bookImageLink, String title, String author,
                                               String publisher, String publicationDate, String loanAvailability) {
        return new LibraryRepositoryResponse(bookImageLink, title, author, publisher, publicationDate,
                loanAvailability);
    }

}
