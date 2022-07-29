package com.example.qalammovement.Model;

import com.google.gson.annotations.SerializedName;

public class AttandenceResponse {

    @SerializedName("class")
    String userclass;

    @SerializedName("time")
    String time;

    @SerializedName("id")
    int id;

    @SerializedName("created_at")
    String date;

    @SerializedName("school_id")
    String school_id;

    @SerializedName("count")
    int count;

    public int getCount() {
        return count;
    }

    public String getSchool_id() {
        return school_id;
    }

    public String getUserclass() {
        return userclass;
    }

    public String getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
