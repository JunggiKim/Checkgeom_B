package io.dodn.springboot.core.api.service;


import io.dodn.springboot.core.api.domain.MoreView;
import io.dodn.springboot.core.api.domain.gyeonggiEducationalElectronicLibrary.gyeonggiEducationalElectronicLibrary;
import io.dodn.springboot.core.api.service.response.LibrarySearchServiceResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriver;
import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriverWait;

@Component
public class GyeonggiEducationalElectronicLibraryReader {


    public Document getGyeonggiEducationalElectronicLibraryHtml (String searchUrl) {
        WebDriver webDriver = openWebBrowser(searchUrl);
        Document document = Jsoup.parse(webDriver.getPageSource());
        webDriver.quit();
        return document;
    }


    private WebDriver openWebBrowser(String basicSearchUrl) {
        WebDriver webDriver = createWebDriver();
        WebDriverWait webDriverWait = createWebDriverWait(webDriver);

        webDriver.get(basicSearchUrl); // 브라우저에서 url로 이동한다.
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className(gyeonggiEducationalElectronicLibrary.stayClassName)));

        return webDriver;
    }

   public List<String> getMoreViewLinks(Document document, String searchUrl) {
        List<String> moreViewLinkList = new ArrayList<>();
        MoreView moreView = gyeonggiEducationalElectronicLibraryIsMoreView(document);

        if (moreView.moreView()) {
            String moreViewUrl = gyeonggiEducationalElectronicLibrary.moreViewSearchUrlCreate(searchUrl, moreView.totalCount());
            moreViewLinkList.add(moreViewUrl);
        }
        return moreViewLinkList;
    }

    public String getBookSearchTotalCount (Document document) {

        return document.select("b#book_totalDataCount").text();
    }


    public List<LibrarySearchServiceResponse.BookDto> getBookItemDtos(Document document) {

        Elements select = document.select("div.row");
        //  제목 , 저자 , 출판사  , 출판날짜  , 대출가능여부  , 책 이미지링크 ,
//    String bookImageLink, String title, String author, String publisher, String publicationDate,

        List<LibrarySearchServiceResponse.BookDto> bookDtoList = new ArrayList<>();


        for (Element element : select) {
            String bookTitle = element.select("a.name.goDetail").text();
            String bookImageLink = element.select("a.goDetail img").attr("src");

            String[] bookDetailInfo = element.select("div p").text().split("│");
            List<String> bookDetailInfoList = Arrays.stream(bookDetailInfo)
                    .map(this::extractBookDetails).toList();

            // 저자, 출판사, 출판일 ,도서관 대출가능여부 (대출 ,예약 두개있을수도 있음) ,자료유형 전자책
            LibrarySearchServiceResponse.BookDto bookDto = LibrarySearchServiceResponse.BookDto.of(
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



    private String extractBookDetails(String bookInfo) {
        int index = bookInfo.indexOf(":");
        String subStringed = bookInfo.substring(index + 1);
        int index1 = subStringed.indexOf(":");  // 경기교육통합도서관대출 가능 여부 :  <- 문자 또 제거
        return subStringed.substring(index1 + 1);
    }



    private MoreView gyeonggiEducationalElectronicLibraryIsMoreView(Document document) {
        String totalCount = document.select("b#book_totalDataCount").text();
        return MoreView.create(Integer.parseInt(totalCount));
    }




}
