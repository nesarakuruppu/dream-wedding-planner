package com.example.dreamweddingplanner1.models;

public class Guest {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String rsvp;

    public Guest(int id, String name, String phone, String email, String rsvp) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.rsvp = rsvp;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getRsvp() { return rsvp; }

    // Setters
    public void setId(int id) { this.id = id; }
}
