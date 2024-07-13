package io.dodn.springboot.core.api.domain;

import io.dodn.springboot.core.api.controller.v1.request.SearchRequest;
import io.dodn.springboot.core.api.domain.response.GyeonggiDoCyberLibraryServiceResponse;
import io.dodn.springboot.storage.db.core.GyeonggiDoCyberLibraryRepository;
import io.dodn.springboot.storage.db.core.response.GyeonggiDoCyberLibraryRepositoryResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriver;
import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriverWait;

@Service
@Transactional
public class GyeonggiDoCyberLibraryService {

    static final String bookListAttributeUlValue = "bookMisc bookGal galTypeList list";
    static final String bookListAttributeUlKey = "class";

    private static final Logger log = LoggerFactory.getLogger(GyeonggiDoCyberLibraryService.class);

    private final GyeonggiDoCyberLibraryRepository gyeonggiDoCyberLibraryRepository;

    public GyeonggiDoCyberLibraryService(GyeonggiDoCyberLibraryRepository gyeonggiDoCyberLibraryRepository) {
        this.gyeonggiDoCyberLibraryRepository = gyeonggiDoCyberLibraryRepository;
    }


    // 소장형이든 구독형 최대 첫화면에서는 6개만 보여준다.
    // 그래서 숫자 값을 찾아서 만약 총값이 6개이상이라면 더보기칸을 눌러서 들어간다

    // search 의 경우 가져오는게 api로 변경이 있을 수 있기에 AOP로 따로 뺴두도록하자 웹드라이버의 기능을
    public GyeonggiDoCyberLibraryServiceResponse search(SearchRequest searchRequest) {

        ChromeDriver webDriver = openWebBrowser(searchRequest);


        List<GyeonggiDoCyberLibraryServiceResponse.BookDto> bookDtoList = gyeonggiDoCyberLibraryRepository.getGyeonggiDoCyberLibraryResponse(webDriver.getPageSource())
                .stream().map(GyeonggiDoCyberLibraryServiceResponse.BookDto::of)
                .toList();

        webDriver.quit();

        return GyeonggiDoCyberLibraryServiceResponse.of(
                bookDtoList,
                bookDtoList.size()
        );

    }



    private void getSearchBookList(Document htmlPage) {
        Elements collectibleBook = htmlPage.select("[data-type=EB]");//소장형


//        collectibleBook.stream().forEach(element ->{
//            Elements title = element.select("h6.title");
//            Elements desc = element.select("p.desc");
//            Elements loanReservationStatus = element.select("div.stat");
//            System.out.println("책 제목 =" + title.toString());
//            System.out.println("책 설명 =" + desc.toString());
//            System.out.println("책 대출예약현황 =" + loanReservationStatus.toString());
//
//        });


        Elements subscriptionBook = htmlPage.select("[data-type=SUBS]"); //구독형
        Elements audioBook = htmlPage.select("[data-type=AB]"); //오디오북


    }

    private ChromeDriver openWebBrowser(SearchRequest searchRequest) {
        String basicSearchUrl = GyeonggiDoCyberLibrary.basicSearchUrlCreate(
                searchRequest.searchType(),
                searchRequest.keyword(),
                searchRequest.listType(),
                searchRequest.sort()
        );

        ChromeDriver webDriver = createWebDriver();
        WebDriverWait webDriverWait = createWebDriverWait(webDriver);

        webDriver.get(basicSearchUrl);    //브라우저에서 url로 이동한다.
        webDriverWait.until(     //동적 리소스를 가져오기 때문에 아래에 지정한 리소스가 생기기전까지 대기를 한다. 대기 하지 않을 경우 빈데이터가 올 수있음
                ExpectedConditions.presenceOfElementLocated(By.className("searchResultBody"))
        );
        return webDriver;
    }

    private List<SearchBookType> getMoreViewSearchBookTypeList(Document htmlPage) {
        return htmlPage.select("h5.searchH").stream()
                .map(element -> SearchBookType.extraction(element.toString()))
                .filter(SearchBookType::isMoreView)
                .toList();
    }


    private static void extracted(Elements collectibleBook) {

        Elements attr = collectibleBook.attr(bookListAttributeUlKey, bookListAttributeUlValue);
        attr.forEach(element -> log.info(element.toString()));
    }

}
