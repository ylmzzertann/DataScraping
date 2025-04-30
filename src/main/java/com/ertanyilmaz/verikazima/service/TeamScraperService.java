package com.ertanyilmaz.verikazima.service;

import com.ertanyilmaz.verikazima.model.Player;
import com.ertanyilmaz.verikazima.model.Team;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamScraperService {

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.7049.115 Safari/537.36");
        return options;
    }

    public List<Team> scrapeTeams() {
        List<Team> teams = new ArrayList<>();
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver(getChromeOptions());
        try {
            driver.get("https://www.transfermarkt.com/super-lig/startseite/wettbewerb/TR1");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.items")));

            List<WebElement> tables = driver.findElements(By.cssSelector("table.items"));
            if (tables.isEmpty()) return teams;

            WebElement teamTable = tables.get(0);
            List<WebElement> rows = teamTable.findElements(By.cssSelector("tbody > tr"));

            for (WebElement row : rows) {
                try {
                    WebElement linkElement = row.findElement(By.cssSelector("td.no-border-links > a"));
                    String teamName = linkElement.getText().trim();
                    String originalLink = linkElement.getAttribute("href");
                    String teamLink = originalLink.replace("/startseite/", "/kader/");

                    teams.add(new Team(teamName, teamLink));
                } catch (NoSuchElementException ignored) {}
            }
        } finally {
            driver.quit();
        }
        return teams;
    }

    public List<Player> scrapePlayersByTeams(List<Team> teams) {
        List<Player> players = new ArrayList<>();
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        for (Team team : teams) {
            WebDriver driver = new ChromeDriver(getChromeOptions());
            try {
                driver.get(team.getLink());

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.items")));

                // ✅ Cookie kabul butonunu tıkla (varsa)
                try {
                    WebElement acceptCookies = driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler"));
                    if (acceptCookies.isDisplayed()) {
                        acceptCookies.click();
                        Thread.sleep(1000);
                    }
                } catch (NoSuchElementException ignored) {}

                List<WebElement> tables = driver.findElements(By.cssSelector("table.items"));
                if (tables.isEmpty()) continue;

                WebElement playerTable = tables.get(0);
                List<WebElement> rows = playerTable.findElements(By.cssSelector("tbody > tr"));

                for (WebElement row : rows) {
                    try {
                        WebElement nameElement = row.findElement(By.cssSelector("td.posrela a.spielprofil_tooltip"));
                        String name = nameElement.getText().trim();

                        WebElement positionElement = row.findElement(By.cssSelector("table.inline-table tr:nth-of-type(2) td"));
                        String position = positionElement.getText().trim();

                        String age = row.findElement(By.cssSelector("td.zentriert:nth-of-type(2)")).getText().trim();

                        WebElement nationalityImg = row.findElement(By.cssSelector("td.zentriert:nth-of-type(3) img"));
                        String nationality = nationalityImg.getAttribute("title");

                        Player player = new Player(name, position, team.getName(), age, nationality);
                        players.add(player);
                    } catch (NoSuchElementException ignored) {}
                }
            } catch (Exception e) {
                System.out.println("❌ " + team.getName() + " takımında hata: " + e.getMessage());
            } finally {
                driver.quit();
            }
        }

        System.out.println("🎯 Çekilen toplam oyuncu sayısı: " + players.size());
        return players;
    }
}
