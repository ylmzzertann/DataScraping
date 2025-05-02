package com.ertanyilmaz.verikazima.service;

import com.ertanyilmaz.verikazima.model.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerScraperService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerScraperService.class);

    public List<Player> scrapePlayersFromHtml(String pageSource, String fallbackTeamName) {
        List<Player> players = new ArrayList<>();
        Document doc = Jsoup.parse(pageSource);

        // 🔍 Sayfa başlığından takım adını çek (örneğin: Galatasaray A takım kadrosu...)
        String teamName = fallbackTeamName;
        try {
            Element heading = doc.selectFirst("div.data-header__headline-wrapper h1");
            if (heading != null) {
                teamName = heading.text().replace(" A takım kadrosu", "").trim(); // Gereksiz kısımları temizle
            }
        } catch (Exception e) {
            logger.warn("Takım adı çekilemedi, fallback değeri kullanılacak: {}", fallbackTeamName);
        }

        Elements playerRows = doc.select("table.items > tbody > tr");

        for (Element row : playerRows) {
            try {
                Element nameElement = row.selectFirst("td.posrela table.inline-table td.hauptlink a");
                String name = (nameElement != null) ? nameElement.text().trim() : "";

                Element positionElement = row.selectFirst("td.posrela table.inline-table tr:nth-of-type(2) td");
                String position = (positionElement != null) ? positionElement.text().trim() : "";

                Element ageElement = row.select("td.zentriert").get(1);
                String age = (ageElement != null) ? ageElement.text().trim() : "";

                Element nationalityElement = row.selectFirst("td.zentriert img");
                String nationality = (nationalityElement != null) ? nationalityElement.attr("title").trim() : "";

                if (!name.isEmpty()) {
                    Player player = new Player(name, position, teamName, age, nationality);
                    players.add(player);
                    logger.info("✅ Player: {} | Position: {} | Age: {} | Nationality: {} | Team: {}", name, position, age, nationality, teamName);
                }
            } catch (Exception e) {
                logger.warn("⚠️ Satırda hata: {}", e.getMessage());
            }
        }

        return players;
    }
}
