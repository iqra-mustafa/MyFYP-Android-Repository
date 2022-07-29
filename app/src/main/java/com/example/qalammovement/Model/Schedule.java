package com.example.qalammovement.Model;

import com.google.gson.annotations.SerializedName;

public class Schedule {
    @SerializedName("id")
    int id;

    @SerializedName("date")
    String date;

    @SerializedName("time")
    String time;

    @SerializedName("subject")
    String subject;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSubject() {
        return subject;
    }
}
