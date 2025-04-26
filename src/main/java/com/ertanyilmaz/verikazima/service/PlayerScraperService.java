package com.ertanyilmaz.verikazima.service;

import com.ertanyilmaz.verikazima.model.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerScraperService {

    public List<Player> scrapePlayersFromHtml(String pageSource) {
        List<Player> players = new ArrayList<>();
        Document doc = Jsoup.parse(pageSource);

        Elements playerRows = doc.select("table.items > tbody > tr");

        for (Element row : playerRows) {
            try {
                // İsim
                Element nameElement = row.selectFirst("td.posrela table.inline-table td.hauptlink a");
                String name = (nameElement != null) ? nameElement.text().trim() : "";

                // Pozisyon
                Element positionElement = row.selectFirst("td.posrela table.inline-table tr:nth-of-type(2) td");
                String position = (positionElement != null) ? positionElement.text().trim() : "";

                // Yaş
                Element ageElement = row.select("td.zentriert").get(1);
                String age = (ageElement != null) ? ageElement.text().trim() : "";

                // Uyruk (sadece 1. img alıyoruz)
                Element nationalityElement = row.selectFirst("td.zentriert img");
                String nationality = (nationalityElement != null) ? nationalityElement.attr("title").trim() : "";

                if (!name.isEmpty()) { // sadece ismi olanları ekleyelim
                    Player player = new Player(name, position, "Galatasaray", age, nationality);
                    players.add(player);
                }
            } catch (Exception e) {
                System.out.println("Satırda hata: " + e.getMessage());
            }
        }

        return players;
    }
}
