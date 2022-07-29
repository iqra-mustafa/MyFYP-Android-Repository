package com.example.qalammovement.Model;

import com.google.gson.annotations.SerializedName;

public class GalleryImages {
    @SerializedName("id")
    int id;

    @SerializedName("image")
    String image;

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }
}
