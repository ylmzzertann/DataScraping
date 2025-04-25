package com.ertanyilmaz.verikazima.controller;

import com.ertanyilmaz.verikazima.model.Player;
import com.ertanyilmaz.verikazima.service.PlayerScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerScraperService scraperService;

    @GetMapping("/scrape")
    public List<Player> scrapeAndGetPlayers() throws Exception {
        return scraperService.scrapePlayers();
    }
}
