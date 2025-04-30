package com.ertanyilmaz.verikazima.controller;

import com.ertanyilmaz.verikazima.model.Player;
import com.ertanyilmaz.verikazima.model.Team;
import com.ertanyilmaz.verikazima.repository.PlayerRepository;
import com.ertanyilmaz.verikazima.service.PlayerScraperService;
import com.ertanyilmaz.verikazima.service.SeleniumPlayerScraper;
import com.ertanyilmaz.verikazima.service.TeamScraperService;
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

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamScraperService teamScraperService;

    // ✅ Sadece Galatasaray oyuncularını kazıyan method
    @GetMapping("/scrape")
    public List<Player> scrapeGalatasarayPlayers() {
        String url = "https://www.transfermarkt.com/galatasaray-istanbul/kader/verein/141/saison_id/2024";
        String pageHtml = seleniumScraper.getPageSource(url);
        List<Player> players = jsoupParser.scrapePlayersFromHtml(pageHtml);

        // Veritabanına kaydet
        playerRepository.saveAll(players);

        return players;
    }

    // ✅ Süper Lig'teki tüm takımların oyuncularını kazıyacak yeni method
    @GetMapping("/scrape-all")
    public List<Player> scrapeAllPlayers() {
        // 1. Tüm Süper Lig takımlarını çek
        List<Team> teams = teamScraperService.scrapeTeams();
        System.out.println("Çekilen takım sayısı: " + teams.size());

        // 2. Tüm takımların oyuncularını çek
        List<Player> players = teamScraperService.scrapePlayersByTeams(teams);
        System.out.println("Çekilen toplam oyuncu sayısı: " + players.size());

        // 3. Veritabanına kaydet
        if (!players.isEmpty()) {
            playerRepository.saveAll(players);
        }

        // 4. JSON olarak döndür
        return players;
    }
}
