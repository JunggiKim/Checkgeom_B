package io.dodn.springboot.core.api.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverUtil {

    private static final String WEB_DRIVER_PATH = "C:\\toy\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe"; // WebDriver
                                                                                                                       // 경로

    private static final String WEB_DRIVER = "webdriver.chrome.driver";

    public static WebDriver createWebDriver() {
        System.setProperty(WEB_DRIVER, WEB_DRIVER_PATH);
        ChromeOptions chromeOptions = createOptions();
        return new ChromeDriver(chromeOptions);
    }

    public static WebDriverWait createWebDriverWait(WebDriver webDriver) {
        return new WebDriverWait(webDriver, Duration.ofMillis(10));
    }

    private static ChromeOptions createOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-popup-blocking"); // 팝업안띄움
        chromeOptions.addArguments("headless"); // 브라우저 안띄움
        chromeOptions.addArguments("--disable-gpu"); // gpu 비활성화
        chromeOptions.addArguments("--blink-settings=imagesEnabled=false"); // 이미지 로딩을
                                                                            // 실행하지 않음
        chromeOptions.addArguments("--mute-audio"); // 음소거 옵션
        return chromeOptions;
    }

}
