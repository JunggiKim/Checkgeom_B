package io.dodn.springboot.core.api.service;

import io.dodn.springboot.core.api.domain.MoreView;
import io.dodn.springboot.core.api.domain.gyeonggiEducationalElectronicLibrary.gyeonggiEducationalElectronicLibrary;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibrary;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryMoreViewType;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryReader;
import io.dodn.springboot.core.api.domain.response.LibraryServiceResponse;
import io.dodn.springboot.core.api.domain.smallbusinesslibrary.SmallBusinessLibrary;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriver;
import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriverWait;

@Service
@Transactional
public class LibraryService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader;

    public LibraryService(GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader) {
        this.gyeonggiDoCyberLibraryReader = gyeonggiDoCyberLibraryReader;
    }

    // 소장형이든 구독형 최대 첫화면에서는 6개만 보여준다.
    // 그래서 숫자 값을 찾아서 만약 총값이 6개이상이라면 더보기칸을 눌러서 들어간다

    // gyeonggiDoCyberLibrarySearch 의 경우 가져오는게 api로 변경이 있을 수 있기에 AOP로 따로 뺴두도록하자 웹드라이버의 기능을 빼 둘수가있나 한번 알아보자
    public LibraryServiceResponse gyeonggiDoCyberLibrarySearch(String keyword) {

        String basicSearchUrl = GyeonggiDoCyberLibrary.basicSearchUrlCreate(keyword);
        WebDriver webDriver = openWebBrowser(basicSearchUrl, GyeonggiDoCyberLibrary.stayClassName);

        List<GyeonggiDoCyberLibraryMoreViewType> moreViewList = gyeonggiDoCyberLibraryReader.isMoreViewList(webDriver.getPageSource());
        boolean isMoreView = moreViewList.stream().anyMatch(GyeonggiDoCyberLibraryMoreViewType::isMoreView);

        List<String> moreViewLink = new ArrayList<>();
        if (isMoreView) {
            moreViewLink = moreViewList.stream()
                    .map(viewType -> GyeonggiDoCyberLibrary.moreViewSearchUrlCreate(keyword, viewType))
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
        WebDriver webDriver = openWebBrowser(searchUrl, "smain");

        List<String> moreViewLinkList = new ArrayList<>();
        MoreView moreView = gyeonggiEducationalElectronicLibraryIsMoreView(webDriver.getPageSource());

        if (moreView.moreView()) {
            String moreViewUrl = gyeonggiEducationalElectronicLibrary.moreViewSearchUrlCreate(searchUrl, moreView.totalCount());
            moreViewLinkList.add(moreViewUrl);
        }

        List<LibraryServiceResponse.BookDto> bookItemDtos = getBookItemDtos(webDriver.getPageSource());

        webDriver.quit();
        return LibraryServiceResponse.of(bookItemDtos, bookItemDtos.size(), moreViewLinkList);
    }

    private MoreView gyeonggiEducationalElectronicLibraryIsMoreView(String pageSource) {
        Document parse = Jsoup.parse(pageSource);
        String totalCount = parse.select("b#book_totalDataCount").text();

        return MoreView.create(Integer.parseInt(totalCount));
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

        return bookDtoList;
    }

    private static String extractBookDetails(String bookInfo) {
        int index = bookInfo.indexOf(":");
        String subStringed = bookInfo.substring(index + 1);
        int index1 = subStringed.indexOf(":");  // 경기교육통합도서관대출 가능 여부 :  <- 문자 또 제거
        String bookDetailInfo2 = subStringed.substring(index1 + 1);
        return bookDetailInfo2;
    }

    public LibraryServiceResponse smallBusinessLibrarySearch(String searchKeyword) {

        // TODO 기본 URL 불러오기까지는 가능하게 해놨다.
        //   이제 값을 가져오기만 하면 될듯 하다.

        String basicUrl = SmallBusinessLibrary.basicUrlCreate(searchKeyword);
        WebDriver webDriver = openWebBrowser(basicUrl, "contents");

        Document document = Jsoup.parse(webDriver.getPageSource());
        String totalCount = document.select("div.book_resultTxt p").toString().replaceAll("[^0-9]", "");


        MoreView moreView = MoreView.create(Integer.parseInt(totalCount));

        List<String> moreViewUrlList = new ArrayList<>();
        List<LibraryServiceResponse.BookDto> bookDtoList = getSmallBusinessLibraryBookItemDtos(webDriver.getPageSource());

        if (moreView.moreView()) {
            String moreViewUrl = SmallBusinessLibrary.moreViewUrlCreate(basicUrl, totalCount);
            moreViewUrlList.add(moreViewUrl);
        }


        webDriver.quit();

        return LibraryServiceResponse.of(bookDtoList , bookDtoList.size() ,moreViewUrlList);

    }

    private List<LibraryServiceResponse.BookDto> getSmallBusinessLibraryBookItemDtos(String htmlPage) {
        Document document = Jsoup.parse(htmlPage);
        Elements selectBookList = document.select("ul.book_resultList > li");

        return selectBookList.stream().map(LibraryService::SmallBusinessLibraryMapBookDto).toList();

    }

    private static LibraryServiceResponse.BookDto SmallBusinessLibraryMapBookDto(Element element) {
        String bookImageLink = element.select("a.scale img").attr("src");
        String bookTitle = element.select("li.tit a").text();
        String bookDetailInfo = element.select("li.writer").toString();
        Pattern pattern = Pattern.compile("<li class=\"writer\">(.*?)<span>(.*?)</span>(\\d{4}-\\d{2}-\\d{2})</li>");
        Matcher matcher = pattern.matcher(bookDetailInfo);
        if (matcher.find()) {
            String author = matcher.group(1); // 저자
            String publisher = matcher.group(2); // 출판사
            String publicationDate = matcher.group(3); // 출판 날짜
            return LibraryServiceResponse.BookDto.of(
                    bookImageLink,
                    bookTitle,
                    author,
                    publisher,
                    publicationDate,
                    "대출 가능"
            );
        }
        throw new RuntimeException(element + " 의 정상적인 변환을 하지 못했습니다.");
    }
}
