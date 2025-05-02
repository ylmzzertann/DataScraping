package com.ertanyilmaz.verikazima.service;

import com.ertanyilmaz.verikazima.model.Player;
import com.ertanyilmaz.verikazima.model.Team;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TeamScraperService {

    private static final Logger logger = LoggerFactory.getLogger(TeamScraperService.class);

    @Autowired
    private SeleniumPlayerScraper seleniumScraper;

    @Autowired
    private PlayerScraperService playerScraperService;

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080", "--no-sandbox", "--disable-dev-shm-usage");
        return options;
    }

    public List<Team> scrapeTeams() {
        List<Team> teams = new ArrayList<>();
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver(getChromeOptions());

        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("https://www.transfermarkt.com.tr/super-lig/startseite/wettbewerb/TR1");

            WebElement table = driver.findElement(By.cssSelector("table.items"));
            List<WebElement> rows = table.findElements(By.cssSelector("tbody > tr"));

            for (WebElement row : rows) {
                try {
                    List<WebElement> linkElements = row.findElements(By.cssSelector("td.no-border-links > a"));
                    if (!linkElements.isEmpty()) {
                        WebElement linkElement = linkElements.get(0);
                        String name = linkElement.getText().trim();
                        String href = linkElement.getAttribute("href").replace("/startseite/", "/kader/");
                        teams.add(new Team(name, href));
                        logger.info("✅ Team found: {} -> {}", name, href);
                    }
                } catch (Exception e) {
                    logger.warn("⚠️ Failed to parse team row: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error("❌ Error while scraping teams: {}", e.getMessage());
        } finally {
            driver.quit();
        }

        return teams;
    }

    public List<Player> scrapePlayersByTeams(List<Team> teams) {
        List<Player> players = new ArrayList<>();

        for (Team team : teams) {
            try {
                String pageHtml = seleniumScraper.getPageSource(team.getLink());
                List<Player> teamPlayers = playerScraperService.scrapePlayersFromHtml(pageHtml, team.getName());
                players.addAll(teamPlayers);
            } catch (Exception e) {
                logger.error("❌ Error scraping team {}: {}", team.getName(), e.getMessage());
            }
        }

        logger.info("🎯 Total players scraped: {}", players.size());
        return players;
    }
}
