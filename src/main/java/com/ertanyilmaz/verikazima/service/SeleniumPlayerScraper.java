package com.ertanyilmaz.verikazima.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SeleniumPlayerScraper {

    public String getPageSource(String url) {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(url);

            // Cookie kabul et (varsa)
            try {
                WebElement cookieButton = driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler"));
                cookieButton.click();
                Thread.sleep(1000);
            } catch (Exception ignored) {}

            return driver.getPageSource();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            driver.quit();
        }
    }

    // ✅ Takım adını doğrudan Selenium ile çek
    public String getTeamName(String url) {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(url);

            // Cookie kabul et (varsa)
            try {
                WebElement cookieButton = driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler"));
                cookieButton.click();
                Thread.sleep(1000);
            } catch (Exception ignored) {}

            WebElement h1 = driver.findElement(By.cssSelector("div.data-header__headline-container h1"));
            return h1.getText().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "Takım adı çekilemedi";
        } finally {
            driver.quit();
        }
    }
}
