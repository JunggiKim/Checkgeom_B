package io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary;

import io.dodn.springboot.core.api.domain.response.LibraryServiceResponse;
import io.dodn.springboot.storage.db.core.GyeonggiDoCyberLibraryRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GyeonggiDoCyberLibraryReader {

    private static final Logger log = LoggerFactory.getLogger(GyeonggiDoCyberLibraryReader.class);
    private final GyeonggiDoCyberLibraryRepository gyeonggiDoCyberLibraryRepository;

    public GyeonggiDoCyberLibraryReader(GyeonggiDoCyberLibraryRepository gyeonggiDoCyberLibraryRepository) {
        this.gyeonggiDoCyberLibraryRepository = gyeonggiDoCyberLibraryRepository;
    }


    public List<LibraryServiceResponse.BookDto> getSearchData(WebDriver webDriver) {

        String htmlPage = webDriver.getPageSource();

        return gyeonggiDoCyberLibraryRepository
                .getGyeonggiDoCyberLibraryResponse(htmlPage)
                .stream()
                .map(LibraryServiceResponse.BookDto::of)
                .toList();

    }


    public List<GyeonggiDoCyberLibraryMoreViewType> isMoreViewList(String pageHtml) {
        Elements totalSearchBook = Jsoup.parse(pageHtml).select("h5.searchH");
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
        System.out.println("숫자 = " + moreViewTotalCount);
        GyeonggiDoCyberLibraryBookType bookType = GyeonggiDoCyberLibraryBookType.of(findHtmlBookType);

        return GyeonggiDoCyberLibraryMoreViewType.of(
                bookType,
                Integer.parseInt(moreViewTotalCount)
        );
    }


}
