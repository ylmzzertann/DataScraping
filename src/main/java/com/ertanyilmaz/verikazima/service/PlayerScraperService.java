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

    public List<Player> scrapePlayersFromHtml(String html) {
        List<Player> players = new ArrayList<>();
        Document doc = Jsoup.parse(html);

        Elements rows = doc.select("table.items > tbody > tr");

        for (Element row : rows) {
            try {
                // İç içe tablo yapısına göre doğru selector'lar
                Element inlineTable = row.selectFirst("td.posrela table.inline-table");
                if (inlineTable == null) continue;

                String name = inlineTable.select("td.hauptlink > a").text();
                String position = inlineTable.select("tr:nth-of-type(2) > td").text();

                String age = row.select("td.zentriert:nth-of-type(2)").text();
                String nationality = row.select("td.zentriert:nth-of-type(3) img").attr("title");

                Player player = new Player(name, position, "Galatasaray", age, nationality);
                players.add(player);

            } catch (Exception e) {
                System.out.println("Hatalı satır atlandı: " + e.getMessage());
            }
        }

        return players;
    }
}
