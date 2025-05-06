package com.ertanyilmaz.verikazima.controller;

import com.ertanyilmaz.verikazima.model.Team;
import com.ertanyilmaz.verikazima.service.SeleniumPlayerScraper;
import com.ertanyilmaz.verikazima.service.TeamScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private SeleniumPlayerScraper seleniumPlayerScraper;

    @Autowired
    private TeamScraperService teamScraperService;

    @GetMapping("/scrape")
    public List<Team> scrapeTeamsFromEspn(@RequestParam String url) {
        String html = seleniumPlayerScraper.getPageSource(url);
        return teamScraperService.scrapeTeamsFromEspn(html);
    }
}
