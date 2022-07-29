package com.example.qalammovement.Model;

import com.google.gson.annotations.SerializedName;

public class StudentResponse {
    @SerializedName("id")
    int id;

    @SerializedName("status")
    boolean status;

    @SerializedName("message")
    String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
