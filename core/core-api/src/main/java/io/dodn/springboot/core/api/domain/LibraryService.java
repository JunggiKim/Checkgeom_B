package io.dodn.springboot.core.api.domain;

import io.dodn.springboot.core.api.controller.v1.request.SearchRequest;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibrary;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryMoreViewType;
import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibraryReader;
import io.dodn.springboot.core.api.domain.request.SearchServiceRequest;
import io.dodn.springboot.core.api.domain.response.GyeonggiDoCyberLibraryServiceResponse;
import io.dodn.springboot.storage.db.core.GyeonggiDoCyberLibraryRepository;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriver;
import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriverWait;

@Service
@Transactional
public class LibraryService {

    private static final Logger log = LoggerFactory.getLogger(LibraryService.class);

    private final GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader;

    public LibraryService(GyeonggiDoCyberLibraryReader gyeonggiDoCyberLibraryReader) {
        this.gyeonggiDoCyberLibraryReader = gyeonggiDoCyberLibraryReader;
    }

    // 소장형이든 구독형 최대 첫화면에서는 6개만 보여준다.
    // 그래서 숫자 값을 찾아서 만약 총값이 6개이상이라면 더보기칸을 눌러서 들어간다

    // search 의 경우 가져오는게 api로 변경이 있을 수 있기에 AOP로 따로 뺴두도록하자 웹드라이버의 기능을 빼 둘수가있나 한번 알아보자
    public GyeonggiDoCyberLibraryServiceResponse search(SearchServiceRequest searchRequest) {
        String basicSearchUrl = GyeonggiDoCyberLibrary.basicSearchUrlCreate(searchRequest.keyword(),
                searchRequest.searchType(), searchRequest.listType(), searchRequest.sort());
        String stayType = "div.searchResultBody";
        WebDriver webDriver = openWebBrowser(basicSearchUrl , stayType);


        List<GyeonggiDoCyberLibraryMoreViewType> moreViewList = gyeonggiDoCyberLibraryReader.isMoreViewList(webDriver.getPageSource());

        boolean isMoreView = moreViewList.stream().anyMatch(GyeonggiDoCyberLibraryMoreViewType::isMoreView);

        List<String> moreViewLink = new ArrayList<>();
        if(isMoreView){
            moreViewLink = moreViewList.stream()
                    .map(viewType -> GyeonggiDoCyberLibrary.moreViewSearchUrlCreate(basicSearchUrl, viewType))
                    .toList();
        }

        List<GyeonggiDoCyberLibraryServiceResponse.BookDto> bookDtoList = gyeonggiDoCyberLibraryReader.getSearchData(webDriver);

        webDriver.quit();
        return GyeonggiDoCyberLibraryServiceResponse.of(bookDtoList, bookDtoList.size() , moreViewLink);
    }

    private void moreViewBook(List<GyeonggiDoCyberLibraryMoreViewType> moreViewList, String basicSearchUrl) {
        for (GyeonggiDoCyberLibraryMoreViewType moreViewBook : moreViewList) {
             if (moreViewBook.isNotMoreView()) continue;
            String moreViewUrl = GyeonggiDoCyberLibrary.moreViewSearchUrlCreate(basicSearchUrl, moreViewBook);
            //타입에 맞는 URL
            String moreViewTag= "div.searchResultList";
            WebDriver moreViewWebDriver = openWebBrowser(moreViewUrl, moreViewTag);
            String moreViewHtml = moreViewWebDriver.getPageSource();
            gyeonggiDoCyberLibraryReader.getSearchData(moreViewWebDriver);
            // 더보기 링크 에 맞는 브라우저 오픈
        }
    }

    // collectibleBook.stream().forEach(element ->{
    // Elements title = element.select("h6.title");
    // Elements desc = element.select("p.desc");
    // Elements loanReservationStatus = element.select("div.stat");
    // System.out.println("책 제목 =" + title.toString());
    // System.out.println("책 설명 =" + desc.toString());
    // System.out.println("책 대출예약현황 =" + loanReservationStatus.toString());
    //
    // });

    private void getSearchBookList(Document htmlPage) {
        Elements collectibleBook = htmlPage.select("[data-type=EB]");// 소장형

        Elements subscriptionBook = htmlPage.select("[data-type=SUBS]"); // 구독형
        Elements audioBook = htmlPage.select("[data-type=AB]"); // 오디오북

    }

    private WebDriver openWebBrowser(String basicSearchUrl , String stayClass) {

        WebDriver webDriver = createWebDriver();
        WebDriverWait webDriverWait = createWebDriverWait(webDriver);

        webDriver.get(basicSearchUrl); // 브라우저에서 url로 이동한다.
        webDriverWait.until( // 동적 리소스를 가져오기 때문에 아래에 지정한 리소스가 생기기전까지 대기를 한다. 대기 하지 않을 경우
                             // 빈데이터가 올 수있음
                ExpectedConditions.presenceOfElementLocated(By.className(stayClass)));
        return webDriver;
    }

    private List<SearchBookType> getMoreViewSearchBookTypeList(Document htmlPage) {
        return htmlPage.select("h5.searchH")
            .stream()
            .map(element -> SearchBookType.extraction(element.toString()))
            .filter(SearchBookType::isMoreView)
            .toList();
    }

    private static void extracted(Elements collectibleBook) {

        final String bookListAttributeUlValue = "bookMisc bookGal galTypeList list";
        final String bookListAttributeUlKey = "class";

        Elements attr = collectibleBook.attr(bookListAttributeUlKey, bookListAttributeUlValue);
        attr.forEach(element -> log.info(element.toString()));
    }

}
