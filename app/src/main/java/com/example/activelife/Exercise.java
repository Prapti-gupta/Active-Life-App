package com.example.activelife;

public class Exercise {
    private int id; // Add this line
    private String name;

    public Exercise(int id, String name) { // Update constructor
        this.id = id;
        this.name = name;
    }

    public int getId() { // Optional: Add getter for ID
        return id;
    }

    public String getName() {
        return name;
    }
}
