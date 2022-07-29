package com.example.qalammovement.Model;

import com.google.gson.annotations.SerializedName;

public class StudentModel {
    @SerializedName("id")
    int id;

    @SerializedName("S_Name")
    String name;

    @SerializedName("S_Gender")
    String gender;

    @SerializedName("S_FatherName")
    String fathername;

    @SerializedName("S_ContactNumber")
    String contact;

    @SerializedName("S_FatherOccupation")
    String fatheroccupation;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getFathername() {
        return fathername;
    }

    public String getContact() {
        return contact;
    }

    public String getFatheroccupation() {
        return fatheroccupation;
    }
}
