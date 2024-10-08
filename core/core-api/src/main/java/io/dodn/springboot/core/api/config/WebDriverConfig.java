//package io.dodn.springboot.core.api.config;
//
//import java.time.Duration;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class WebDriverConfig {
//
//	private final String WEB_DRIVER_PATH =
//		"C:\\toy\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe"; // WebDriver 경로
//	private final String WEB_DRIVER = "webdriver.chrome.driver";
//
//	// webDriver 옵션 설정
//	@Bean
//	public WebDriver WebDriver() {
//		System.setProperty(WEB_DRIVER, WEB_DRIVER_PATH);
//		ChromeOptions chromeOptions = createOptions();
//		return new ChromeDriver(chromeOptions);
//	}
//
//	@Bean
//	public WebDriverWait webDriverWait(WebDriver webDriver) {
//		return new WebDriverWait(webDriver, Duration.ofMillis(8));
//	}
//
//	private static ChromeOptions createOptions() {
//		ChromeOptions chromeOptions = new ChromeOptions();
//		chromeOptions.addArguments("--disable-popup-blocking"); //팝업안띄움
//		chromeOptions.addArguments("headless"); //브라우저 안띄움
//		chromeOptions.addArguments("--disable-gpu"); //gpu 비활성화
//		chromeOptions.addArguments("--blink-settings=imagesEnabled=false");
//		return chromeOptions;
//	}
//
//}
//
