package com.example.qalammovement.Model;

import com.google.gson.annotations.SerializedName;

public class School {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("location")
    String location;

    @SerializedName("timing")
    String timing;

    @SerializedName("image")
    String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getTiming() {
        return timing;
    }

    public String getImage() {
        return image;
    }
}
