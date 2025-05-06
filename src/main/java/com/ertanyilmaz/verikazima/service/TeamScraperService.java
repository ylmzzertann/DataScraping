package com.ertanyilmaz.verikazima.service;

import com.ertanyilmaz.verikazima.model.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamScraperService {

    public List<Team> scrapeTeamsFromEspn(String html) {
        List<Team> teams = new ArrayList<>();
        Document doc = Jsoup.parse(html);

        Elements links = doc.select("a[href^=/soccer/team/squad/_/id/]");

        for (Element link : links) {
            String teamName = link.text().trim();
            String href = link.attr("href").trim();
            String fullUrl = "https://www.espn.com" + href;

            teams.add(new Team(teamName, fullUrl));
        }

        return teams;
    }
}
