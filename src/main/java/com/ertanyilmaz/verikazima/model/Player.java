package com.ertanyilmaz.verikazima.model;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nationality;
    private String age;
    private String position;
    private String team;
    private String height;
    private String weight;

    public Player() {
    }

    public Player(String name, String nationality, String age, String position, String team, String height, String weight) {
        this.name = name;
        this.nationality = nationality;
        this.age = age;
        this.position = position;
        this.team = team;
        this.height = height;
        this.weight = weight;
    }

    // Getters and setters...

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getNationality() { return nationality; }
    public String getAge() { return age; }
    public String getPosition() { return position; }
    public String getTeam() { return team; }
    public String getHeight() { return height; }
    public String getWeight() { return weight; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setAge(String age) { this.age = age; }
    public void setPosition(String position) { this.position = position; }
    public void setTeam(String team) { this.team = team; }
    public void setHeight(String height) { this.height = height; }
    public void setWeight(String weight) { this.weight = weight; }
}
