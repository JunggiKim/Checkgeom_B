package io.dodn.springboot.core.api.service;

import io.dodn.springboot.core.api.domain.SearchType;
import io.dodn.springboot.core.api.service.response.AllLibraryServiceResponse;
import io.dodn.springboot.core.api.service.response.LibrarySearchServiceResponse;

public interface LibrarySearchService {
	LibrarySearchServiceResponse gyeonggiDoCyberLibrarySearch(String searchKeyword);

	LibrarySearchServiceResponse gyeonggiEducationalElectronicLibrarySearch(String searchKeyword);

	LibrarySearchServiceResponse smallBusinessLibrarySearch(String searchKeyword);

	AllLibraryServiceResponse allLibraryAsyncSearch(String searchKeyword);
}
