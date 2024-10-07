package io.dodn.springboot.core.api.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverUtil {

    public static WebDriver createWebDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(createOptions());
    }

    public static WebDriverWait createWebDriverWait(WebDriver webDriver) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(1));
    }

    private static ChromeOptions createOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-popup-blocking"); // 팝업안띄움
        chromeOptions.addArguments("headless"); // 브라우저 안띄움
        chromeOptions.addArguments("--disable-gpu"); // gpu 비활성화
        chromeOptions.addArguments("--blink-settings=imagesEnabled=false"); // 이미지 로딩을 실행하지 않음
        chromeOptions.addArguments("--mute-audio"); // 음소거 옵션
        return chromeOptions;
    }

}
