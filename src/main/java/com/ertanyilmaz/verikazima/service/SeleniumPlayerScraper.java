package com.ertanyilmaz.verikazima.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class SeleniumPlayerScraper {

    public String getPageSource(String url) {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String pageSource = driver.getPageSource();
        driver.quit();
        return pageSource;
    }
}
