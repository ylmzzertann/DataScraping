package com.ertanyilmaz.verikazima.controller;

import com.ertanyilmaz.verikazima.model.Team;
import com.ertanyilmaz.verikazima.model.Team;
import com.ertanyilmaz.verikazima.service.TeamScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamScraperService teamScraperService;

    @GetMapping("/scrape")
    public List<Team> scrapeTeams() {
        return teamScraperService.scrapeTeams();
    }
}
