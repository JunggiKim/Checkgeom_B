package io.dodn.springboot.core.api.domain;

import io.dodn.springboot.core.api.config.RegUtil;
import io.dodn.springboot.core.api.domain.response.GyeonggiDoCyberLibraryResponse;
import io.dodn.springboot.storage.db.core.GyeonggiDoCyberLibraryRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

    private GyeonggiDoCyberLibraryRepository gyeonggiDoCyberLibraryRepository;


    // 소장형이든 구독형 최대 첫화면에서는 6개만 보여준다.
    // 그래서 숫자 값을 찾아서 만약 총값이 6개이상이라면 더보기칸을 눌러서 들어간다
    public List<GyeonggiDoCyberLibraryResponse> search(String url) {

        ChromeDriver webDriver = openBrowser(url);

        Document htmlPage = Jsoup.parse(webDriver.getPageSource());
        getSearchBookList(htmlPage);

        Elements searchBookItems = htmlPage.select("li.bookItem.row");  // 띄어쓰기를 그냥 .으로 바꿔줘야 인식을 한다.

        List<GyeonggiDoCyberLibraryResponse> bookDtoList = searchBookItems.stream().map(element -> {
            String bookImageLink = element.select("img.bookCover").attr("src");
            String title = element.select("h6.title")
                    .first().children()
                    .first().text();
            List<String> list = Arrays.stream(element.select("p.desc")
                            .text().split("/"))
                    .toList();  //책의 출판정보이다  bookPublishingInformation
            // 순서 :  저자 , 출판사 , 출판 날짜
            String loanReservationStatus = element.select("div.stat").text();
            return GyeonggiDoCyberLibraryResponse.of(
                    bookImageLink,
                    title,
                    list.get(0),
                    list.get(1),
                    list.get(1),
                    loanReservationStatus
            );
        }).toList();

        // 구독형은 최대 가능한 숫자가999씩나오고 최대값이나온다
        // 음 그냥 다같이 보내고 프론트엔드에서 안보여주는걸로할까 고민 이네

        Elements h4 = htmlPage.select("h4.summaryHeading");


        String totalSearchCount = h4.stream()
                .map(element -> RegUtil.deleteHtmlTag(element.toString()))
                .findFirst().orElse("0");

        webDriver.quit();

        return bookDtoList;

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

    private ChromeDriver openBrowser(String url) {
        ChromeDriver webDriver = createWebDriver();
        WebDriverWait webDriverWait = createWebDriverWait(webDriver);

        webDriver.get(url);    //브라우저에서 url로 이동한다.
        webDriverWait.until(     //동적 리소스를 가져오기 때문에 아래에 지정한 리소스가 생기기전까지 대기를 한다.
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
