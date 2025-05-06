package com.ertanyilmaz.verikazima.controller;

import com.ertanyilmaz.verikazima.model.Player;
import com.ertanyilmaz.verikazima.repository.PlayerRepository;
import com.ertanyilmaz.verikazima.service.PlayerScraperService;
import com.ertanyilmaz.verikazima.service.JsonToDatabaseLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerScraperService playerScraperService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private JsonToDatabaseLoader jsonLoader;

    @GetMapping("/scrape-test")
    public List<Player> scrapeTest() {
        String url = "https://www.espn.com/soccer/team/squad/_/id/432/tur.galatasaray";
        String teamName = "Galatasaray";

        List<Player> players = playerScraperService.scrapePlayersFromESPN(url, teamName);
        playerRepository.saveAll(players);
        jsonLoader.saveToJsonFile(players, "players.json");

        return players;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}
