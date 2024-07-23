package io.dodn.springboot.storage.db.core;

import io.dodn.springboot.storage.db.core.response.LibraryRepositoryResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class GyeonggiDoCyberLibraryRepository {


    public List<LibraryRepositoryResponse> getGyeonggiDoCyberLibraryResponse(Element htmlBody) {

        Elements searchBookItems = htmlBody.select("li.bookItem.row");
        return searchBookItems.stream()
            .map(GyeonggiDoCyberLibraryRepository::getGyeonggiDoCyberLibraryRepositoryResponse)
            .toList();
    }

    private static LibraryRepositoryResponse getGyeonggiDoCyberLibraryRepositoryResponse(
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

}
