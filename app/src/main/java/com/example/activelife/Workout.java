package com.example.activelife;

import java.io.Serializable;

public class Workout implements Serializable {
    private static final long serialVersionUID = 1L; // Unique ID for serialization
    private int id;
    private String name;
    private int imageResId;
    private String time;

    // Constructor
    public Workout(int id, String name, int imageResId, String time) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
        this.time = time;
    }

    // Getters
    public int getId() {
        return id; // Returns the workout ID
    }

    public String getName() {
        return name; // Returns the workout name
    }

    public int getImageResId() {
        return imageResId; // Returns the image resource ID
    }

    public String getTime() {
        return time; // Returns the workout time
    }
}
