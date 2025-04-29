package com.ertanyilmaz.verikazima.service;

import com.ertanyilmaz.verikazima.model.Team;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamScraperService {

    public List<Team> scrapeTeams() {
        List<Team> teams = new ArrayList<>();

        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.transfermarkt.com/super-lig/startseite/wettbewerb/TR1");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> tables = driver.findElements(By.cssSelector("table.items")); 

     // Sadece ilk tabloyu alıyoruz (takım listesi)
     WebElement teamTable = tables.get(0); 

     List<WebElement> rows = teamTable.findElements(By.cssSelector("tbody > tr"));

     for (WebElement row : rows) {
         try {
             WebElement linkElement = row.findElement(By.cssSelector("td.no-border-links > a"));
             String teamName = linkElement.getText();
             String teamLink = linkElement.getAttribute("href");

             teams.add(new Team(teamName, teamLink));
             System.out.println(teamName + " -> " + teamLink);
         } catch (Exception e) {
             System.out.println("Satır atlandı: " + e.getMessage());
         }
     }


        driver.quit();
        return teams;
    }
}
