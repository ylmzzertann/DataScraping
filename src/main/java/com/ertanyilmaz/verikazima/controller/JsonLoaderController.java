package com.ertanyilmaz.verikazima.controller;

import com.ertanyilmaz.verikazima.service.JsonToDatabaseLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JsonLoaderController {

    @Autowired
    private JsonToDatabaseLoader jsonToDatabaseLoader;

    @GetMapping("/json-load")
    public String loadJsonToDb() {
        jsonToDatabaseLoader.loadPlayersFromJson("players.json"); // players.json kök dizindeyse
        return "✅ JSON verisi başarıyla veritabanına kaydedildi.";
    }
}
