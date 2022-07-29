package com.example.qalammovement.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {

    private static MyPreferences myPreferences;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static MyPreferences getPreferences(Context context) {
        if (myPreferences == null) myPreferences = new MyPreferences(context);
        return myPreferences;
    }

    public void setUserName(String userName){
        editor.putString("username", userName);
        editor.apply();
    }

    public String getUserName(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString("username", "Name not found");
    }

    public void setEmail(String email){
        editor.putString("email", email);
        editor.apply();
    }

    public String getEmail(){
        return sharedPreferences.getString("email", null); //if user's age not found then it'll return -1
    }

    public void setInstitution(String institution){
        editor.putString("institution", institution);
        editor.apply();
    }

    public String getInstitution(){
        return sharedPreferences.getString("institution", null); //if user's age not found then it'll return -1
    }

    public void setDay(String Day){
        editor.putString("day", Day);
        editor.apply();
    }

    public String getDay(){
        return sharedPreferences.getString("day", null); //if user's age not found then it'll return -1
    }

    public void setDesignation(String designation){
        editor.putString("designation", designation);
        editor.apply();
    }

    public String getDesignation(){
        return sharedPreferences.getString("designation", null); //assume the default value is false
    }
    public void setID(int id){
        editor.putInt("id", id);
        editor.apply();
    }

    public int getID(){
        return sharedPreferences.getInt("id", -1); //assume the default value is false
    }

    public void setToken(String token){
        editor.putString("token", token);
        editor.apply();
    }

    public String getToken(){
        return sharedPreferences.getString("token", null); //assume the default value is false
    }
    public  void logout(){
        editor.remove("email");
        editor.commit();
    }


}
