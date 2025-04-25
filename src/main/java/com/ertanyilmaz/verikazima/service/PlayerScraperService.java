package com.ertanyilmaz.verikazima.service;

import com.ertanyilmaz.verikazima.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerScraperService {

    public List<Player> scrapePlayers() throws Exception {
        List<Player> players = new ArrayList<>();
        String url = "https://www.transfermarkt.com/super-lig/startseite/wettbewerb/TR1"; // örnek link

        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();

        Elements rows = doc.select("table.items tbody tr");

        for (Element row : rows) {
            String name = row.select(".hauptlink a").text();
            String position = row.select("td:nth-of-type(4)").text();
            String team = row.select("td:nth-of-type(5) a").text();

            if (!name.isEmpty()) {
                players.add(new Player(name, position, team));
            }
        }

        // JSON olarak kaydet
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("players.json"), players);

        return players;
    }
}
