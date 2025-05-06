package com.ertanyilmaz.verikazima.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String url;

    public Team() {
    }

    public Team(String name, String url) {
        this.name = name;
        this.url = url;
    }

    // Getters and setters...

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getUrl() { return url; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setUrl(String url) { this.url = url; }
}
