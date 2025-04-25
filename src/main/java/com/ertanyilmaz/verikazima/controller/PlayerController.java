package com.ertanyilmaz.verikazima.controller;

import com.ertanyilmaz.verikazima.model.Player;
import com.ertanyilmaz.verikazima.service.PlayerScraperService;
import com.ertanyilmaz.verikazima.service.SeleniumPlayerScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private SeleniumPlayerScraper seleniumScraper;

    @Autowired
    private PlayerScraperService jsoupParser;

    @GetMapping("/scrape")
    public List<Player> scrapePlayers() {
        String url = "https://www.transfermarkt.com/galatasaray-istanbul/kader/verein/141/saison_id/2024";
        String pageHtml = seleniumScraper.getPageSource(url);
        return jsoupParser.scrapePlayersFromHtml(pageHtml);
    }
}
