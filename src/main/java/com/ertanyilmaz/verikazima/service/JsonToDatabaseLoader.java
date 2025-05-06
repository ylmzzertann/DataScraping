package com.ertanyilmaz.verikazima.service;

import com.ertanyilmaz.verikazima.model.Player;
import com.ertanyilmaz.verikazima.repository.PlayerRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class JsonToDatabaseLoader {

    @Autowired
    private PlayerRepository playerRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // JSON'dan veritabanına veri yükleme (proje ana dizinindeki dosya)
    public void loadFromJson(String jsonPath) {
        try (FileInputStream inputStream = new FileInputStream(jsonPath)) {
            List<Player> players = objectMapper.readValue(inputStream, new TypeReference<>() {});
            playerRepository.saveAll(players);
            System.out.println("Veriler veritabanına başarıyla yüklendi.");
        } catch (Exception e) {
            System.err.println("JSON yükleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Veritabanına kaydedilecek verileri JSON dosyasına yaz
    public void saveToJsonFile(List<Player> players, String outputPath) {
        try {
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(players);
            try (FileOutputStream fos = new FileOutputStream(new File(outputPath))) {
                fos.write(jsonString.getBytes(StandardCharsets.UTF_8));
                System.out.println("Veriler players.json dosyasına yazıldı.");
            }
        } catch (Exception e) {
            System.err.println("JSON yazma hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
