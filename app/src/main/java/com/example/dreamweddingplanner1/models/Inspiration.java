package com.example.dreamweddingplanner1.models;

public class Inspiration {
    private String imageUrl;
    private String description;

    public Inspiration(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
}