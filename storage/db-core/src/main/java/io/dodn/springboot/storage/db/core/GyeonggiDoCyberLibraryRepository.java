package io.dodn.springboot.storage.db.core;

import io.dodn.springboot.storage.db.core.response.GyeonggiDoCyberLibraryRepositoryResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class GyeonggiDoCyberLibraryRepository {

    public List<GyeonggiDoCyberLibraryRepositoryResponse> getGyeonggiDoCyberLibraryResponse(String html) {

        Document htmlPage = Jsoup.parse(html);
        Elements searchBookItems = htmlPage.select("li.bookItem.row"); // 띄어쓰기를 그냥 .으로
                                                                       // 바꿔줘야 인식을 한다.

        return searchBookItems.stream()
            .map(GyeonggiDoCyberLibraryRepository::getGyeonggiDoCyberLibraryRepositoryResponse)
            .toList();
    }

    private static GyeonggiDoCyberLibraryRepositoryResponse getGyeonggiDoCyberLibraryRepositoryResponse(
            Element htmlElement) {
        String bookImageLink = getBookImgeLink(htmlElement);
        String title = getBookTitle(htmlElement);
        // 인덱스 순서는 작성자 , 출판사 ,출판 날짜
        List<String> bookPublishingInformationList = getBookPublishingInformationList(htmlElement);
        String loanReservationStatus = getLoanReservationStatus(htmlElement);

        return GyeonggiDoCyberLibraryRepositoryResponse.of(bookImageLink, title, bookPublishingInformationList.get(0),
                bookPublishingInformationList.get(1), bookPublishingInformationList.get(1), loanReservationStatus);
    }

    private static String getLoanReservationStatus(Element htmlElement) {
        return htmlElement.select("div.stat").text();
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

}
