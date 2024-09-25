package io.dodn.springboot.core.api.service.response;

import io.dodn.springboot.storage.db.core.response.LibraryRepositoryResponse;

import java.util.List;

public record LibrarySearchServiceResponse(List<LibrarySearchServiceResponse.BookDto> bookDtoList,
										   int bookSearchTotalCount,
										   List<String> moreViewLink,
										   String libraryTypeText) {

	public static LibrarySearchServiceResponse of(List<LibrarySearchServiceResponse.BookDto> bookList,
		int bookSearchTotalCount, List<String> moreViewLink, String libraryTypeText) {
		return new LibrarySearchServiceResponse(bookList, bookSearchTotalCount, moreViewLink, libraryTypeText);
	}

	public record BookDto(String bookImageLink, String title, String author, String publisher, String publicationDate,
						  String loanAvailability) {

		public static LibrarySearchServiceResponse.BookDto of(
			LibraryRepositoryResponse repositoryResponse) {

			return new LibrarySearchServiceResponse.BookDto(repositoryResponse.bookImageLink(),
				repositoryResponse.title(), repositoryResponse.author(), repositoryResponse.publisher(),
				repositoryResponse.publicationDate(), repositoryResponse.loanAvailability());
		}

		public static LibrarySearchServiceResponse.BookDto of(
			String bookImageLink,
			String title,
			String author,
			String publisher,
			String publicationDate,
			String loanAvailability
		) {
			return new LibrarySearchServiceResponse.BookDto(
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