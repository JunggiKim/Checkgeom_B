package io.dodn.springboot.core.api.service;

import io.dodn.springboot.core.api.domain.MoreView;
import io.dodn.springboot.core.api.domain.smallbusinesslibrary.SmallBusinessLibrary;
import io.dodn.springboot.core.api.service.response.LibrarySearchServiceResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriver;
import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriverWait;

@Component
public class SmallBusinessLibraryReader {



    public Element getHtmlBody(String basicUrl) {
        WebDriver webDriver = openWebBrowser(basicUrl, "contents");
        Element htmlBody = Jsoup.parse(webDriver.getPageSource()).body();
        webDriver.quit();
        return htmlBody;
    }


    private WebDriver openWebBrowser(String basicSearchUrl, String stayClassName) {
        WebDriver webDriver = createWebDriver();
        WebDriverWait webDriverWait = createWebDriverWait(webDriver);

        webDriver.get(basicSearchUrl); // 브라우저에서 url로 이동한다.
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className(stayClassName)));

        return webDriver;
    }


    public List<LibrarySearchServiceResponse.BookDto> getBooks(Element htmlBody) {
        Elements selectBookList = htmlBody.select("ul.book_resultList > li");

        return selectBookList.stream().map(this::mapBookDto).toList();

    }

    private LibrarySearchServiceResponse.BookDto mapBookDto(Element element) {
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

    public int getTotalCount (Element htmlBody) {
        String totalCount = htmlBody.select("div.book_resultTxt p").toString().replaceAll("[^0-9]", "");
        String stringTotalCount = totalCount.isBlank() ? "0" : totalCount;
        return Integer.parseInt(stringTotalCount);
}


    public List<String> getMoreViewLinks(String searchKeyword, int totalCount) {
        MoreView moreView = MoreView.create(totalCount);

        List<String> moreViewUrlList = new ArrayList<>();

        if (moreView.moreView()) {
            String moreViewUrl = SmallBusinessLibrary.moreViewUrlCreate(searchKeyword, totalCount);
            moreViewUrlList.add(moreViewUrl);
        }

        return moreViewUrlList;
    }
}
