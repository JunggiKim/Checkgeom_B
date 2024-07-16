package io.dodn.springboot.core.api.domain;

import io.dodn.springboot.core.api.domain.gyeonggiEducationalElectronicLibrary.gyeonggiEducationalElectronicLibrary;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibrary;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryMoreViewType;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryReader;
import io.dodn.springboot.core.api.domain.request.SearchServiceRequest;
import io.dodn.springboot.core.api.domain.response.LibraryServiceResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriver;
import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriverWait;

@Service
@Transactional
public class LibraryService {

    private static final Logger log = LoggerFactory.getLogger(LibraryService.class);

    private final GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader;

    public LibraryService(GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader) {
        this.gyeonggiDoCyberLibraryReader = gyeonggiDoCyberLibraryReader;
    }

    // 소장형이든 구독형 최대 첫화면에서는 6개만 보여준다.
    // 그래서 숫자 값을 찾아서 만약 총값이 6개이상이라면 더보기칸을 눌러서 들어간다

    // gyeonggiDoCyberLibrarySearch 의 경우 가져오는게 api로 변경이 있을 수 있기에 AOP로 따로 뺴두도록하자 웹드라이버의 기능을 빼 둘수가있나 한번 알아보자
    public LibraryServiceResponse gyeonggiDoCyberLibrarySearch(SearchServiceRequest searchRequest) {

        String basicSearchUrl = GyeonggiDoCyberLibrary.basicSearchUrlCreate(searchRequest.keyword(),
                searchRequest.searchType(), searchRequest.listType(), searchRequest.sort());

        WebDriver webDriver = openWebBrowser(basicSearchUrl, GyeonggiDoCyberLibrary.stayClassName);

        List<GyeonggiDoCyberLibraryMoreViewType> moreViewList = gyeonggiDoCyberLibraryReader.isMoreViewList(webDriver.getPageSource());

        boolean isMoreView = moreViewList.stream().anyMatch(GyeonggiDoCyberLibraryMoreViewType::isMoreView);

        List<String> moreViewLink = new ArrayList<>();
        if (isMoreView) {
            moreViewLink = moreViewList.stream()
                    .map(viewType -> GyeonggiDoCyberLibrary.moreViewSearchUrlCreate(basicSearchUrl, viewType))
                    .toList();
        }

        List<LibraryServiceResponse.BookDto> bookDtoList = gyeonggiDoCyberLibraryReader.getSearchData(webDriver);

        webDriver.quit();
        return LibraryServiceResponse.of(bookDtoList, bookDtoList.size(), moreViewLink);
    }


    // 경기도사이버도서관 더 보기에 맞는 모든 북을 가져오는 로직
    private void moreViewBook(List<GyeonggiDoCyberLibraryMoreViewType> moreViewList, String basicSearchUrl) {
        for (GyeonggiDoCyberLibraryMoreViewType moreViewBook : moreViewList) {
            if (moreViewBook.isNotMoreView()) continue;
            String moreViewUrl = GyeonggiDoCyberLibrary.moreViewSearchUrlCreate(basicSearchUrl, moreViewBook);
            //타입에 맞는 URL
            String moreViewTag = "searchResultList";
            WebDriver moreViewWebDriver = openWebBrowser(moreViewUrl, moreViewTag);
            String moreViewHtml = moreViewWebDriver.getPageSource();
            gyeonggiDoCyberLibraryReader.getSearchData(moreViewWebDriver);
            // 더보기 링크 에 맞는 브라우저 오픈
        }
    }

    // 경기도사이버도서관 더보기에 맞는 유형에 대한 로직
    private void getSearchBookList(Document htmlPage) {
        Elements collectibleBook = htmlPage.select("[data-type=EB]");// 소장형
        Elements subscriptionBook = htmlPage.select("[data-type=SUBS]"); // 구독형
        Elements audioBook = htmlPage.select("[data-type=AB]"); // 오디오북

    }

    private WebDriver openWebBrowser(String basicSearchUrl, String stayClassName) {
        WebDriver webDriver = createWebDriver();
        WebDriverWait webDriverWait = createWebDriverWait(webDriver);

        webDriver.get(basicSearchUrl); // 브라우저에서 url로 이동한다.
        webDriverWait.until( // 동적 리소스를 가져오기 때문에 아래에 지정한 리소스가 생기기전까지 대기를 한다. 대기 하지 않을 경우
                // 빈데이터가 올 수있음
                ExpectedConditions.presenceOfElementLocated(By.className(stayClassName)));
        return webDriver;
    }


    // 기본 검색한 책 목록과 책 총 결과 수 와 검색결과 모두 볼수있는 더보기링크 까지 보내주자
    public LibraryServiceResponse gyeonggiEducationalElectronicLibrarySearch(String keyword) {


        String searchUrl = gyeonggiEducationalElectronicLibrary.basicSearchUrlCreate(keyword);


        List<String> moreViewLink = new ArrayList<>();

        WebDriver webDriver = openWebBrowser(searchUrl, "smain");

        List<LibraryServiceResponse.BookDto> bookItemDtos = getBookItemDtos(webDriver.getPageSource());




        webDriver.quit();

        return LibraryServiceResponse.of(bookItemDtos,bookItemDtos.size(),List.of());
    }

    private List<LibraryServiceResponse.BookDto> getBookItemDtos(String htmlPage) {

        Document document = Jsoup.parse(htmlPage);
        Elements select = document.select("div.row");

        //  제목 , 저자 , 출판사  , 출판날짜  , 대출가능여부  , 책 이미지링크 ,
//    String bookImageLink, String title, String author, String publisher, String publicationDate,

        List<LibraryServiceResponse.BookDto> bookDtoList = new ArrayList<>();


        for (Element element : select) {
            String bookTitle = element.select("a.name.goDetail").text();
            String bookImageLink = element.select("a.goDetail img").attr("src");

            String[] bookDetailInfo = element.select("div p").text().split("│");
            List<String> bookDetailInfoList = Arrays.stream(bookDetailInfo)
                    .map(LibraryService::extractBookDetails).toList();

            // 저자, 출판사, 출판일 ,도서관 대출가능여부 (대출 ,예약 두개있을수도 있음) ,자료유형 전자책
            LibraryServiceResponse.BookDto bookDto = LibraryServiceResponse.BookDto.of(
                    bookImageLink,
                    bookTitle,
                    bookDetailInfoList.get(0),
                    bookDetailInfoList.get(1),
                    bookDetailInfoList.get(2),
                    bookDetailInfoList.get(3)
            );

            bookDtoList.add(bookDto);
            //            인덱스 순서
        }

        bookDtoList.forEach(bookDto -> System.out.println(bookDto.toString()));



        return bookDtoList;
    }

    private static String extractBookDetails(String bookInfo) {
        int index = bookInfo.indexOf(":");
        String subStringed = bookInfo.substring(index + 1);
        int index1 = subStringed.indexOf(":");  // 경기교육통합도서관대출 가능 여부 :  <- 문자 또 제거
        String bookDetailInfo2 = subStringed.substring(index1 + 1);
        return bookDetailInfo2;
    }
}
