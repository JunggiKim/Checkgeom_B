package io.dodn.springboot.storage.db.core.response;

public record GyeonggiDoCyberLibraryRepositoryResponse(String bookImageLink, String title, String author,
        String publisher, String publicationDate, String loanAvailability) {

    public static GyeonggiDoCyberLibraryRepositoryResponse of(String bookImageLink, String title, String author,
            String publisher, String publicationDate, String loanAvailability) {
        return new GyeonggiDoCyberLibraryRepositoryResponse(bookImageLink, title, author, publisher, publicationDate,
                loanAvailability);
    }

}
