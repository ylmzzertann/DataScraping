package com.ertanyilmaz.verikazima.model;

public class Player {
    private String name;
    private String position;
    private String team;
    private String age;
    private String nationality;

    public Player() {}

    public Player(String name, String position, String team, String age, String nationality) {
        this.name = name;
        this.position = position;
        this.team = team;
        this.age = age;
        this.nationality = nationality;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
}
