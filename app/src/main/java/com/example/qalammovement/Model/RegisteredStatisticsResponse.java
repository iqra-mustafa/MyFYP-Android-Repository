package com.example.qalammovement.Model;

import com.google.gson.annotations.SerializedName;

public class RegisteredStatisticsResponse {
    @SerializedName("id")
    int id;

    @SerializedName("status")
    boolean status;

    @SerializedName("message")
    String message;

    @SerializedName("Registered_Students")
    String registered_student;
    @SerializedName("Registered_Volunteers")
    String registered_volunteer;
    @SerializedName("Registered_Institutions")
    String registered_school;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRegistered_student() {
        return registered_student;
    }

    public void setRegistered_student(String registered_student) {
        this.registered_student = registered_student;
    }

    public String getRegistered_volunteer() {
        return registered_volunteer;
    }

    public void setRegistered_volunteer(String registered_volunteer) {
        this.registered_volunteer = registered_volunteer;
    }

    public String getRegistered_school() {
        return registered_school;
    }

    public void setRegistered_school(String registered_school) {
        this.registered_school = registered_school;
    }

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
