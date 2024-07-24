package io.dodn.springboot.core.api.service;

import io.dodn.springboot.core.api.domain.gyeonggidocyberlibrary.GyeonggiDoCyberLibrary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriver;
import static io.dodn.springboot.core.api.util.WebDriverUtil.createWebDriverWait;

@Component
public class WebBrowserReader {

    public Element gyeonggiDoCyberLibraryOpenBrowserAndGetHtmlBody(String keyword) {
        String basicSearchUrl = GyeonggiDoCyberLibrary.basicSearchUrlCreate(keyword);
        WebDriver webDriver = gyeonggiDoCyberLibraryOpenWebBrowser(basicSearchUrl);
        Element htmlBody = Jsoup.parse(webDriver.getPageSource()).body();
        webDriver.quit();
        return htmlBody;
    }


    private WebDriver gyeonggiDoCyberLibraryOpenWebBrowser(String basicSearchUrl) {
        WebDriver webDriver = createWebDriver();
        WebDriverWait webDriverWait = createWebDriverWait(webDriver);

        webDriver.get(basicSearchUrl); // 브라우저에서 url로 이동한다.
        webDriverWait.until(ExpectedConditions.textMatches(By.cssSelector(GyeonggiDoCyberLibrary.STAY_CSS), Pattern.compile("\\d+")));

        return webDriver;
    }


}
