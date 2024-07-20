package io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary;

import io.dodn.springboot.core.api.service.response.LibraryServiceResponse;
import io.dodn.springboot.storage.db.core.GyeonggiDoCyberLibraryRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GyeonggiDoCyberLibraryReader {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final GyeonggiDoCyberLibraryRepository gyeonggiDoCyberLibraryRepository;

    public GyeonggiDoCyberLibraryReader(GyeonggiDoCyberLibraryRepository gyeonggiDoCyberLibraryRepository) {
        this.gyeonggiDoCyberLibraryRepository = gyeonggiDoCyberLibraryRepository;
    }


    public List<LibraryServiceResponse.BookDto> getSearchData(Element htmlBody) {
        return gyeonggiDoCyberLibraryRepository
                .getGyeonggiDoCyberLibraryResponse(htmlBody).stream()
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

        return GyeonggiDoCyberLibraryMoreViewType.of(bookType);
    }


}
