package com.example.qalammovement.Model;

import com.google.gson.annotations.SerializedName;

public class VolunteerModel {
    @SerializedName("id")
    int id;

    public boolean isStatus() {
        return status;
    }

    @SerializedName("token")
    String token;

    public String getMessage() {
        return message;
    }

    @SerializedName("status")
    boolean status;

    @SerializedName("message")
    String message;

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("V_ContactNumber")
    String contactnumber;

    @SerializedName("V_CNIC")
    String cnic;

    @SerializedName("V_PresentAddress")
    String address;

    @SerializedName("V_UniversityName")
    String universityname;

    @SerializedName("V_Degree")
    String degree;

    @SerializedName("V_Semester")
    String semester;

    @SerializedName("V_TeamName")
    String teamname;

    @SerializedName("V_Day")
    String day;

    @SerializedName("V_InstituteName")
    String institutename;



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

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUniversityname() {
        return universityname;
    }

    public void setUniversityname(String universityname) {
        this.universityname = universityname;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getInstitutename() {
        return institutename;
    }

    public void setInstitutename(String institutename) {
        this.institutename = institutename;
    }
}
