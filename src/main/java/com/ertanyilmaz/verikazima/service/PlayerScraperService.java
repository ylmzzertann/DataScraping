package com.ertanyilmaz.verikazima.service;

import com.ertanyilmaz.verikazima.model.Player;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerScraperService {

    public List<Player> scrapePlayersFromESPN(String url, String teamName) {
        List<Player> players = new ArrayList<>();

        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080", "--no-sandbox", "--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(url);
            List<WebElement> rows = driver.findElements(By.cssSelector("tbody tr.Table__TR"));

            for (WebElement row : rows) {
                try {
                    List<WebElement> cells = row.findElements(By.cssSelector("td.Table__TD"));
                    if (cells.size() >= 6) {
                        String name = "";
                        String number = "";
                        try {
                            WebElement nameAnchor = cells.get(0).findElement(By.cssSelector("a.AnchorLink"));
                            name = nameAnchor.getText().trim();
                        } catch (NoSuchElementException e) {
                            System.out.println("İsim verisi bulunamadı.");
                        }

                        try {
                            WebElement span = cells.get(0).findElement(By.cssSelector("span.p12.n10"));
                            number = span.getText().trim();
                        } catch (NoSuchElementException e) {
                            System.out.println("Numara verisi bulunamadı.");
                        }

                        String position = cells.get(1).getText().trim();
                        String age = cells.get(2).getText().trim();
                        String height = cells.get(3).getText().trim();
                        String weight = cells.get(4).getText().trim();
                        String nationality = cells.get(5).getText().trim();

                        // Oyuncu ismi boşsa bu satırı atla
                        if (!name.isEmpty()) {
                            Player player = new Player(name, nationality, age, position, teamName, height, weight);
                            players.add(player);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Satır atlandı: " + e.getMessage());
                }
            }

        } finally {
            driver.quit();
        }

        return players;
    }
}
