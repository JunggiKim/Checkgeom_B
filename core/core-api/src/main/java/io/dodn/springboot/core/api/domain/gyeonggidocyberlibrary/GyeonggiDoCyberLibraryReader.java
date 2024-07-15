package io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary;

import io.dodn.springboot.core.api.domain.response.GyeonggiDoCyberLibraryServiceResponse;
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

    private static final Logger log = LoggerFactory.getLogger(GyeonggiDoCyberLibraryReader.class);
    private final GyeonggiDoCyberLibraryRepository gyeonggiDoCyberLibraryRepository;

    public GyeonggiDoCyberLibraryReader(GyeonggiDoCyberLibraryRepository gyeonggiDoCyberLibraryRepository) {
        this.gyeonggiDoCyberLibraryRepository = gyeonggiDoCyberLibraryRepository;
    }


    public  List<GyeonggiDoCyberLibraryServiceResponse.BookDto> getSearchData(WebDriver webDriver) {

        String htmlPage = webDriver.getPageSource();

        return gyeonggiDoCyberLibraryRepository
                .getGyeonggiDoCyberLibraryResponse(htmlPage)
                .stream()
                .map(GyeonggiDoCyberLibraryServiceResponse.BookDto::of)
                .toList();

    }


    public List<GyeonggiDoCyberLibraryMoreViewType> isMoreViewList(String pageHtml) {
        Elements totalSearchBook = Jsoup.parse(pageHtml).select("h5.searchH");

        return totalSearchBook.stream()
                .map(GyeonggiDoCyberLibraryReader::mapGyeonggiDoCyberLibraryMoreViewType)
                .toList();
    }

    private static GyeonggiDoCyberLibraryMoreViewType mapGyeonggiDoCyberLibraryMoreViewType(Element element) {
        String childText = element.child(0).text();

        String moreViewTotalCount = childText.replaceAll("[^0-9]", "");

        int index = childText.indexOf("(");
        String findHtmlBookType = (index >= 0) ? childText.substring(0, index) : childText;
        GyeonggiDoCyberLibraryBookType bookType = GyeonggiDoCyberLibraryBookType.of(findHtmlBookType);

        return GyeonggiDoCyberLibraryMoreViewType.of(
                bookType,
                Integer.parseInt(moreViewTotalCount)
        );
    }


}
