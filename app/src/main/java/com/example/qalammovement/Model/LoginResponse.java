package com.example.qalammovement.Model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("id")
    int id;

    @SerializedName("token")
    String token;

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("status")
    boolean status;

    @SerializedName("message")
    String messages;

    @SerializedName("V_Day")
    String day;

    @SerializedName("V_InstituteName")
    String institutename;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDay() { return day; }

    public void setDay(String day) { this.day = day; }

    public String getInstitutename() { return institutename; }

    public void setInstitutename(String institutename) { this.institutename = institutename; }
}
