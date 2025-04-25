package com.ertanyilmaz.verikazima.model;

public class Player {
    private String name;
    private String position;
    private String team;

    // Constructor
    public Player(String name, String position, String team) {
        this.name = name;
        this.position = position;
        this.team = team;
    }

    // Getters ve Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }
}
