package com.ertanyilmaz.verikazima.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SeleniumPlayerScraper {

    public String getPageSource(String url) {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(url);

            // ✅ Sayfa yüklendikten sonra page source al
            String pageHtml = driver.getPageSource();

            return pageHtml;
        } finally {
            // ✅ Mutlaka en son kapat! getPageSource ALINDIKTAN SONRA!
            driver.quit();
        }
    }
}
