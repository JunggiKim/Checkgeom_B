package io.dodn.springboot.core.api.service;

import io.dodn.springboot.core.api.domain.LibraryType;
import io.dodn.springboot.core.api.domain.gyeonggiEducationalElectronicLibrary.gyeonggiEducationalElectronicLibrary;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryMoreViewType;
import io.dodn.springboot.core.api.service.response.AllLibraryServiceResponse;
import io.dodn.springboot.core.api.service.response.LibrarySearchServiceResponse;
import io.dodn.springboot.core.api.domain.smallbusinesslibrary.SmallBusinessLibrary;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class LibraryService {


    // TODO EXAMPLE
    //      대출 가능을 트루 펄스로 값을 보내는것으로 하자
    //      모든 도서관 검색결과 기능도 구현하기
    //      추가 더보기해야 할 것들은 이벤트를 발행을 하고
    //      검색을 한 후 레디스의 Map 타입으로 값을 넣고 그 안에서도 인덱스로 값을 찾으면서 무한스크롤 구현 한번 해 보자

    public LibraryService(GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader, GyeonggiEducationalElectronicLibraryReader gyeonggiEducationalElectronicLibraryReader, SmallBusinessLibraryReader smallBusinessLibraryReader) {
        this.gyeonggiDoCyberLibraryReader = gyeonggiDoCyberLibraryReader;
        this.gyeonggiEducationalElectronicLibraryReader = gyeonggiEducationalElectronicLibraryReader;
        this.smallBusinessLibraryReader = smallBusinessLibraryReader;
    }

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader;
    private final GyeonggiEducationalElectronicLibraryReader gyeonggiEducationalElectronicLibraryReader;
    private final SmallBusinessLibraryReader smallBusinessLibraryReader;


    // 소장형이든 구독형 최대 첫화면에서는 6개만 보여준다.
    // 그래서 숫자 값을 찾아서 만약 총값이 6개이상이라면 더보기칸을 눌러서 들어간다

    // gyeonggiDoCyberLibrarySearch 의 경우 가져오는게 api로 변경이 있을 수 있기에 AOP로 따로 뺴두도록하자 웹드라이버의 기능을 빼 둘수가있나 한번 알아보자
    public LibrarySearchServiceResponse gyeonggiDoCyberLibrarySearch(String keyword) {

       final Element htmlBody = gyeonggiDoCyberLibraryReader.getGyeonggiDoCyberLibraryHtmlBody(keyword);

       final List<String> moreViewLink = gyeonggiDoCyberLibraryReader.getMoreViewLinks(keyword, htmlBody);

       final List<LibrarySearchServiceResponse.BookDto> bookDtoList = gyeonggiDoCyberLibraryReader.searchBookList(htmlBody);

       final int bookSearchTotalCount = gyeonggiDoCyberLibraryReader.getBookSearchTotalCount(htmlBody);

        return LibrarySearchServiceResponse.of(bookDtoList, bookSearchTotalCount, moreViewLink, LibraryType.GYEONGGIDO_CYBER.getText());
    }

    @Async
    public CompletableFuture<LibrarySearchServiceResponse> asyncGyeonggiDoCyberLibrarySearch(String keyword) {

        CompletableFuture.completedFuture("my result");
       final Element htmlBody = gyeonggiDoCyberLibraryReader.getGyeonggiDoCyberLibraryHtmlBody(keyword);

       final List<String> moreViewLink = gyeonggiDoCyberLibraryReader.getMoreViewLinks(keyword, htmlBody);

       final List<LibrarySearchServiceResponse.BookDto> bookDtoList = gyeonggiDoCyberLibraryReader.searchBookList(htmlBody);

       final int bookSearchTotalCount = gyeonggiDoCyberLibraryReader.getBookSearchTotalCount(htmlBody);


        LibrarySearchServiceResponse response = LibrarySearchServiceResponse.of(bookDtoList, bookSearchTotalCount, moreViewLink, LibraryType.GYEONGGIDO_CYBER.getText());



        return CompletableFuture.completedFuture(response);
    }


    // 경기도사이버도서관 더 보기에 맞는 모든 북을 가져오는 로직
    private void moreViewBook(List<GyeonggiDoCyberLibraryMoreViewType> moreViewList, String basicSearchUrl) {
        for (GyeonggiDoCyberLibraryMoreViewType moreViewBook : moreViewList) {
//            if (moreViewBook.isNotMoreView()) continue;
//            String moreViewUrl = GyeonggiDoCyberLibrary.moreViewSearchUrlCreate(basicSearchUrl, moreViewBook);
            //타입에 맞는 URL
            String moreViewTag = "searchResultList";
//            WebDriver moreViewWebDriver = openWebBrowser(moreViewUrl, moreViewTag);
//            Document document = Jsoup.parse(moreViewWebDriver.getPageSource());
//            gyeonggiDoCyberLibraryReader.gyeonggiDoCyberLibraryGetBookInfo(document);
            // 더보기 링크 에 맞는 브라우저 오픈
        }
    }

    // 경기도사이버도서관 더보기에 맞는 유형에 대한 로직
    private void getSearchBookList(Document htmlPage) {
        Elements collectibleBook = htmlPage.select("[data-type=EB]");// 소장형
        Elements subscriptionBook = htmlPage.select("[data-type=SUBS]"); // 구독형
        Elements audioBook = htmlPage.select("[data-type=AB]"); // 오디오북

    }


    // 기본 검색한 책 목록과 책 총 결과 수 와 검색결과 모두 볼수있는 더보기링크 까지 보내주자
    public LibrarySearchServiceResponse gyeonggiEducationalElectronicLibrarySearch(String keyword) {

       final String searchUrl = gyeonggiEducationalElectronicLibrary.basicSearchUrlCreate(keyword);

       final Document document = gyeonggiEducationalElectronicLibraryReader.getGyeonggiEducationalElectronicLibraryHtml(searchUrl);

       final List<String> moreViewLinkList = gyeonggiEducationalElectronicLibraryReader.getMoreViewLinks(document, searchUrl);

       final List<LibrarySearchServiceResponse.BookDto> bookItemDtos = gyeonggiEducationalElectronicLibraryReader.getBookItemDtos(document);

       final String totalCount = gyeonggiEducationalElectronicLibraryReader.getBookSearchTotalCount(document);

        return LibrarySearchServiceResponse.of(bookItemDtos, Integer.parseInt(totalCount), moreViewLinkList, LibraryType.GYEONGGI_EDUCATIONAL_ELECTRONIC.getText());
    }

    @Async
    public LibrarySearchServiceResponse asyncGyeonggiEducationalElectronicLibrarySearch(String keyword) {

       final String searchUrl = gyeonggiEducationalElectronicLibrary.basicSearchUrlCreate(keyword);

       final Document document = gyeonggiEducationalElectronicLibraryReader.getGyeonggiEducationalElectronicLibraryHtml(searchUrl);

       final List<String> moreViewLinkList = gyeonggiEducationalElectronicLibraryReader.getMoreViewLinks(document, searchUrl);

       final List<LibrarySearchServiceResponse.BookDto> bookItemDtos = gyeonggiEducationalElectronicLibraryReader.getBookItemDtos(document);

       final String totalCount = gyeonggiEducationalElectronicLibraryReader.getBookSearchTotalCount(document);

        return LibrarySearchServiceResponse.of(bookItemDtos, Integer.parseInt(totalCount), moreViewLinkList, LibraryType.GYEONGGI_EDUCATIONAL_ELECTRONIC.getText());
    }


    public LibrarySearchServiceResponse smallBusinessLibrarySearch(String searchKeyword) {

        // TODO 기본 URL 불러오기까지는 가능하게 해놨다.
        //   이제 값을 가져오기만 하면 될듯 하다.

        final String basicUrl = SmallBusinessLibrary.basicUrlCreate(searchKeyword);

        final Element htmlBody = smallBusinessLibraryReader.getHtmlBody(basicUrl);

        final List<LibrarySearchServiceResponse.BookDto> bookDtoList = smallBusinessLibraryReader.getBooks(htmlBody);

        final int totalCount = smallBusinessLibraryReader.getTotalCount(htmlBody);

        final List<String> moreViewUrlList = smallBusinessLibraryReader.getMoreViewLinks(searchKeyword, totalCount);


        return LibrarySearchServiceResponse.of(bookDtoList, totalCount, moreViewUrlList, LibraryType.SMALL_BUSINESS.getText());

    }
    public LibrarySearchServiceResponse asyncSmallBusinessLibrarySearch(String searchKeyword) {

        // TODO 기본 URL 불러오기까지는 가능하게 해놨다.
        //   이제 값을 가져오기만 하면 될듯 하다.

        final String basicUrl = SmallBusinessLibrary.basicUrlCreate(searchKeyword);

        final Element htmlBody = smallBusinessLibraryReader.getHtmlBody(basicUrl);

        final List<LibrarySearchServiceResponse.BookDto> bookDtoList = smallBusinessLibraryReader.getBooks(htmlBody);

        final int totalCount = smallBusinessLibraryReader.getTotalCount(htmlBody);

        final List<String> moreViewUrlList = smallBusinessLibraryReader.getMoreViewLinks(searchKeyword, totalCount);


        return LibrarySearchServiceResponse.of(bookDtoList, totalCount, moreViewUrlList, LibraryType.SMALL_BUSINESS.getText());

    }


    public AllLibraryServiceResponse allLibrarySearch(String searchKeyword) {

        final List<LibrarySearchServiceResponse> responseList = new ArrayList<>();
        responseList.add(gyeonggiDoCyberLibrarySearch(searchKeyword));
        responseList.add(gyeonggiEducationalElectronicLibrarySearch(searchKeyword));
        responseList.add(smallBusinessLibrarySearch(searchKeyword));

        return AllLibraryServiceResponse.of(responseList, LibraryType.ALL.getText());
    }

    public AllLibraryServiceResponse allLibraryAsyncSearch(String searchKeyword) {
//        CompletableFuture.completedFuture()
        final List<LibrarySearchServiceResponse> responseList = new ArrayList<>();
        responseList.add(asyncGyeonggiDoCyberLibrarySearch(searchKeyword));
        responseList.add(asyncGyeonggiEducationalElectronicLibrarySearch(searchKeyword));
        responseList.add(asyncSmallBusinessLibrarySearch(searchKeyword));

        return AllLibraryServiceResponse.of(responseList, LibraryType.ALL.getText());
    }
}
