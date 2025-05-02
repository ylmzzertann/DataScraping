package com.ertanyilmaz.verikazima.controller;

import com.ertanyilmaz.verikazima.model.Player;
import com.ertanyilmaz.verikazima.model.Team;
import com.ertanyilmaz.verikazima.repository.PlayerRepository;
import com.ertanyilmaz.verikazima.service.PlayerScraperService;
import com.ertanyilmaz.verikazima.service.SeleniumPlayerScraper;
import com.ertanyilmaz.verikazima.service.TeamScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/scrape")
    public List<Player> scrapeGalatasarayPlayers() {
        String url = "https://www.transfermarkt.com.tr/galatasaray-istanbul/kader/verein/141/saison_id/2024";
        String pageHtml = seleniumScraper.getPageSource(url);
        List<Player> players = jsoupParser.scrapePlayersFromHtml(pageHtml, "Galatasaray");

        playerRepository.saveAll(players);
        return players;
    }

    @GetMapping("/scrape-all")
    public List<Player> scrapeAllPlayers() {
        List<Team> teams = teamScraperService.scrapeTeams();
        List<Player> allPlayers = new ArrayList<>();

        for (Team team : teams) {
            try {
                String html = seleniumScraper.getPageSource(team.getLink());
                List<Player> teamPlayers = jsoupParser.scrapePlayersFromHtml(html, team.getName());
                allPlayers.addAll(teamPlayers);
            } catch (Exception e) {
                System.out.println("❌ " + team.getName() + " takımında hata: " + e.getMessage());
            }
        }

        if (!allPlayers.isEmpty()) {
            playerRepository.saveAll(allPlayers);
        }

        return allPlayers;
    }
}
