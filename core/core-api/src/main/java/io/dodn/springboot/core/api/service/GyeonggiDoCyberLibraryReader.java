package io.dodn.springboot.core.api.service;

import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryBookType;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryMoreViewType;
import io.dodn.springboot.core.api.service.response.LibraryServiceResponse;
import io.dodn.springboot.storage.db.core.response.LibraryRepositoryResponse;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class GyeonggiDoCyberLibraryReader {

    private final Logger log = LoggerFactory.getLogger(getClass());



    public List<LibraryServiceResponse.BookDto> getSearchData(Element htmlBody) {
        return getGyeonggiDoCyberLibraryResponse(htmlBody).stream()
                .map(LibraryServiceResponse.BookDto::of)
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



}
