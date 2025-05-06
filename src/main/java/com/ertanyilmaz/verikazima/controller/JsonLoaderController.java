package com.ertanyilmaz.verikazima.controller;

import com.ertanyilmaz.verikazima.service.JsonToDatabaseLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JsonLoaderController {

    @Autowired
    private JsonToDatabaseLoader jsonToDatabaseLoader;

    @GetMapping("/json-load")
    public String loadJsonToDB() {
        jsonToDatabaseLoader.loadFromJson("players.json");
        return "JSON verisi başarıyla veritabanına kaydedildi.";
    }
}
