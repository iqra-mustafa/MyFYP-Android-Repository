package com.example.qalammovement.Model;

import com.google.gson.annotations.SerializedName;

public class NotificationModel {
    @SerializedName("id")
    int id;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("created_at")
    String created_at;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated_at() {
        return created_at;
    }
}
