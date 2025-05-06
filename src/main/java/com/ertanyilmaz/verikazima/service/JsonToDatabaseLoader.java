package com.ertanyilmaz.verikazima.service;

import com.ertanyilmaz.verikazima.model.Player;
import com.ertanyilmaz.verikazima.repository.PlayerRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class JsonToDatabaseLoader {

    @Autowired
    private PlayerRepository playerRepository;

    public void loadPlayersFromJson(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Player> players = mapper.readValue(new File(filePath), new TypeReference<List<Player>>() {});
            
            if (players != null && !players.isEmpty()) {
                playerRepository.saveAll(players);
                System.out.println("✅ " + players.size() + " oyuncu veritabanına başarıyla eklendi.");
            } else {
                System.out.println("⚠️ JSON dosyası boş veya hatalı.");
            }
        } catch (Exception e) {
            System.out.println("❌ JSON dosyası okunamadı: " + e.getMessage());
        }
    }
}
