package io.dodn.springboot.core.api.service;

import io.dodn.springboot.core.api.domain.LibraryType;
import io.dodn.springboot.core.api.domain.MoreView;
import io.dodn.springboot.core.api.domain.gyeonggiEducationalElectronicLibrary.gyeonggiEducationalElectronicLibrary;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryMoreViewType;
import io.dodn.springboot.core.api.service.response.AllLibraryServiceResponse;
import io.dodn.springboot.core.api.service.response.LibrarySearchServiceResponse;
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


    // TODO EXAMPLE
    //      대출 가능을 트루 펄스로 값을 보내는것으로 하자
    //      모든 도서관 검색결과 기능도 구현하기
    //      추가 더보기해야 할 것들은 이벤트를 발행을 하고
    //      검색을 한 후 레디스의 Map 타입으로 값을 넣고 그 안에서도 인덱스로 값을 찾으면서 무한스크롤 구현 한번 해 보자

    public LibraryService(GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader, GyeonggiEducationalElectronicLibraryReader gyeonggiEducationalElectronicLibraryReader) {
        this.gyeonggiDoCyberLibraryReader = gyeonggiDoCyberLibraryReader;
        this.gyeonggiEducationalElectronicLibraryReader = gyeonggiEducationalElectronicLibraryReader;
    }

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader;
    private final GyeonggiEducationalElectronicLibraryReader gyeonggiEducationalElectronicLibraryReader;



    // 소장형이든 구독형 최대 첫화면에서는 6개만 보여준다.
    // 그래서 숫자 값을 찾아서 만약 총값이 6개이상이라면 더보기칸을 눌러서 들어간다

    // gyeonggiDoCyberLibrarySearch 의 경우 가져오는게 api로 변경이 있을 수 있기에 AOP로 따로 뺴두도록하자 웹드라이버의 기능을 빼 둘수가있나 한번 알아보자
    public LibrarySearchServiceResponse gyeonggiDoCyberLibrarySearch(String keyword) {

        Element htmlBody = gyeonggiDoCyberLibraryReader.getGyeonggiDoCyberLibraryHtmlBody(keyword);

        List<String> moreViewLink = gyeonggiDoCyberLibraryReader.getMoreViewLinks(keyword, htmlBody);

        List<LibrarySearchServiceResponse.BookDto> bookDtoList = gyeonggiDoCyberLibraryReader.searchBookList(htmlBody);

        int bookSearchTotalCount = gyeonggiDoCyberLibraryReader.getBookSearchTotalCount(htmlBody);

        return LibrarySearchServiceResponse.of(bookDtoList, bookSearchTotalCount, moreViewLink , LibraryType.GYEONGGIDO_CYBER.getText());
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

        String searchUrl = gyeonggiEducationalElectronicLibrary.basicSearchUrlCreate(keyword);

        Document document = gyeonggiEducationalElectronicLibraryReader.getGyeonggiEducationalElectronicLibraryHtml(searchUrl);

        List<String> moreViewLinkList = gyeonggiEducationalElectronicLibraryReader.getMoreViewLinks(document, searchUrl);

        List<LibrarySearchServiceResponse.BookDto> bookItemDtos = gyeonggiEducationalElectronicLibraryReader.getBookItemDtos(document);

        String totalCount =  gyeonggiEducationalElectronicLibraryReader.getBookSearchTotalCount(document);

        return LibrarySearchServiceResponse.of(bookItemDtos, Integer.parseInt(totalCount), moreViewLinkList , LibraryType.GYEONGGI_EDUCATIONAL_ELECTRONIC.getText());
    }



    public LibrarySearchServiceResponse smallBusinessLibrarySearch(String searchKeyword) {

        // TODO 기본 URL 불러오기까지는 가능하게 해놨다.
        //   이제 값을 가져오기만 하면 될듯 하다.

        String basicUrl = SmallBusinessLibrary.basicUrlCreate(searchKeyword);
        WebDriver webDriver = openWebBrowser(basicUrl, "contents");

        Element htmlBody = Jsoup.parse(webDriver.getPageSource()).body();
        List<LibrarySearchServiceResponse.BookDto> bookDtoList = getSmallBusinessLibraryBookItemDtos(htmlBody);
        webDriver.quit();

        String totalCount = htmlBody.select("div.book_resultTxt p").toString().replaceAll("[^0-9]", "");


        MoreView moreView = MoreView.create(Integer.parseInt(totalCount));

        List<String> moreViewUrlList = new ArrayList<>();

        if (moreView.moreView()) {
            String moreViewUrl = SmallBusinessLibrary.moreViewUrlCreate(searchKeyword, totalCount);
            moreViewUrlList.add(moreViewUrl);
        }

        return LibrarySearchServiceResponse.of(bookDtoList , Integer.parseInt(totalCount) ,moreViewUrlList , LibraryType.SMALL_BUSINESS.getText());

    }


    private WebDriver openWebBrowser(String basicSearchUrl, String stayClassName) {
        WebDriver webDriver = createWebDriver();
        WebDriverWait webDriverWait = createWebDriverWait(webDriver);

        webDriver.get(basicSearchUrl); // 브라우저에서 url로 이동한다.
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className(stayClassName)));

        return webDriver;
    }

    private List<LibrarySearchServiceResponse.BookDto> getSmallBusinessLibraryBookItemDtos(Element htmlBody) {
        Elements selectBookList = htmlBody.select("ul.book_resultList > li");

        return selectBookList.stream().map(LibraryService::SmallBusinessLibraryMapBookDto).toList();

    }

    private static LibrarySearchServiceResponse.BookDto SmallBusinessLibraryMapBookDto(Element element) {
        String bookImageLink = element.select("a.scale img").attr("src");
        String bookTitle = element.select("li.tit a").text();
        String bookDetailInfo = element.select("li.writer").toString();
        Pattern pattern = Pattern.compile("<li class=\"writer\">(.*?)<span>(.*?)</span>(\\d{4}-\\d{2}-\\d{2})</li>");
        Matcher matcher = pattern.matcher(bookDetailInfo);
        if (matcher.find()) {
            String author = matcher.group(1); // 저자
            String publisher = matcher.group(2); // 출판사
            String publicationDate = matcher.group(3); // 출판 날짜
            return LibrarySearchServiceResponse.BookDto.of(
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

    public AllLibraryServiceResponse allLibrarySearch(String searchKeyword) {

        final List<LibrarySearchServiceResponse> responseList = new ArrayList<>();
        responseList.add(smallBusinessLibrarySearch(searchKeyword));
        responseList.add(gyeonggiDoCyberLibrarySearch(searchKeyword));
        responseList.add(gyeonggiEducationalElectronicLibrarySearch(searchKeyword));

        return AllLibraryServiceResponse.of(responseList , LibraryType.ALL.getText());
    }
}
