package io.dodn.springboot.core.api.service;

import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibrary;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryBookType;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryMoreViewType;
import io.dodn.springboot.core.api.service.response.LibrarySearchServiceResponse;
import io.dodn.springboot.storage.db.core.response.LibraryRepositoryResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriver;
import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriverWait;

@Component
public class GyeonggiDoCyberLibraryReader {

    private final WebBrowserReader webBrowserReader;
    private final LibraryBookInfoReader libraryBookInfoReader;


    public GyeonggiDoCyberLibraryReader(WebBrowserReader webBrowserReader, LibraryBookInfoReader libraryBookInfoReader) {
        this.webBrowserReader = webBrowserReader;
        this.libraryBookInfoReader = libraryBookInfoReader;
    }


    private final Logger log = LoggerFactory.getLogger(getClass());


    public List<LibrarySearchServiceResponse.BookDto> searchBookList(Element htmlBody) {
        return getGyeonggiDoCyberLibraryResponse(htmlBody).stream()
                .map(LibrarySearchServiceResponse.BookDto::of)
                .toList();

    }

    public List<LibrarySearchServiceResponse.BookDto> gyeonggiDoCyberLibraryGetBookDataList(Element htmlBody) {
        return getGyeonggiDoCyberLibraryResponse(htmlBody).stream()
                .map(LibrarySearchServiceResponse.BookDto::of)
                .toList();
    }



    public List<GyeonggiDoCyberLibraryMoreViewType> isMoreViewList(Element htmlBody) {
        Elements totalSearchBook = htmlBody.select("h5.searchH");
        List<Element> audiobookFilterList = totalSearchBook.stream().filter(element -> !element.text().contains("오디오북")).toList();

        return audiobookFilterList.stream()
                .map(GyeonggiDoCyberLibraryReader::mapGyeonggiDoCyberLibraryMoreViewType)
                .toList();
    }

    private static GyeonggiDoCyberLibraryMoreViewType mapGyeonggiDoCyberLibraryMoreViewType(Element element) {
        String childText = element.child(0).text();

        String moreViewTotalCount = childText.replaceAll("[^0-9]", "");

        int index = childText.indexOf("(");
        String findHtmlBookType = (index >= 0) ? childText.substring(0, index) : childText;

        if (findHtmlBookType.equals("오디오북")) {
            return null;
        }

        GyeonggiDoCyberLibraryBookType bookType = GyeonggiDoCyberLibraryBookType.of(findHtmlBookType);

        return GyeonggiDoCyberLibraryMoreViewType.of(
                bookType,
                Integer.parseInt(moreViewTotalCount)
        );
    }


    public List<LibraryRepositoryResponse> getGyeonggiDoCyberLibraryResponse(Element htmlBody) {

        Elements searchBookItems = htmlBody.select("li.bookItem.row");
        return searchBookItems.stream()
                .map(this::getGyeonggiDoCyberLibraryRepositoryResponse)
                .toList();
    }

    private LibraryRepositoryResponse getGyeonggiDoCyberLibraryRepositoryResponse(
            Element htmlElement) {
        String bookImageLink = getBookImgeLink(htmlElement);
        String title = getBookTitle(htmlElement);

        // 인덱스 순서는 작성자 , 출판사 ,출판 날짜
        List<String> bookPublishingInformationList = getBookPublishingInformationList(htmlElement);

        String text = htmlElement.select("div.stat").first().text();
//        System.out.println("대출예약 현황 = " + text);    ex : 대출 : 1/5 예약 : 0/5

        String loanReservationStatus = getLoanReservationStatus(htmlElement);

        return LibraryRepositoryResponse.of(
                bookImageLink, title, bookPublishingInformationList.get(0),
                bookPublishingInformationList.get(1), bookPublishingInformationList.get(2), loanReservationStatus);
    }

    private static String getLoanReservationStatus(Element htmlElement) {
        return htmlElement.select("div.stat").first().text();
    }

    private static String getBookImgeLink(Element htmlElement) {
        return htmlElement.select("img.bookCover").attr("src");
    }

    private static String getBookTitle(Element htmlElement) {
        return htmlElement.select("h6.title").first().children().first().text();
    }

    private static List<String> getBookPublishingInformationList(Element htmlElement) {
        return Arrays.stream(htmlElement.select("p.desc").text().split("/")).toList();
    }


    public Element gyeonggiDoCyberLibraryGetHtmlBody(String keyword) {
        String basicSearchUrl = GyeonggiDoCyberLibrary.basicSearchUrlCreate(keyword);
        WebDriver webDriver = openWebBrowser(basicSearchUrl);
        Element htmlBody = Jsoup.parse(webDriver.getPageSource()).body();
        webDriver.quit();
        return htmlBody;
    }


    private WebDriver openWebBrowser(String basicSearchUrl) {
        WebDriver webDriver = createWebDriver();
        WebDriverWait webDriverWait = createWebDriverWait(webDriver);

        webDriver.get(basicSearchUrl); // 브라우저에서 url로 이동한다.
        webDriverWait.until(ExpectedConditions.textMatches(By.cssSelector(GyeonggiDoCyberLibrary.STAY_CSS), Pattern.compile("\\d+")));

        return webDriver;
    }


    public List<String> getMoreViewLinks(String keyword, Element htmlBody) {
        List<GyeonggiDoCyberLibraryMoreViewType> moreViewList = isMoreViewList(htmlBody);

        boolean isMoreView = moreViewList.stream().anyMatch(GyeonggiDoCyberLibraryMoreViewType::isMoreView);

        List<String> moreViewLink = new ArrayList<>();
        if (isMoreView) {
            moreViewLink = moreViewList.stream()
                    .map(viewType -> GyeonggiDoCyberLibrary.moreViewSearchUrlCreate(keyword, viewType))
                    .toList();
        }
        return moreViewLink;
    }



    public int getBookSearchTotalCount(Element htmlBody) {
        String StringTotalCount = htmlBody.select("h4.summaryHeading i").text().replaceAll(",", "");
        return  Integer.parseInt(StringTotalCount);
    }

}
