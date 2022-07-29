package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.LoginResponse;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.R;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity {

    AppCompatButton Signin_Button;
    AppCompatButton Login_Button;
    TextInputEditText name, password;
    String uname, upassword;
    ProgressDialog progressDialog;
    MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        myPreferences = MyPreferences.getPreferences(this);
        progressDialog = new ProgressDialog(this);
        name = findViewById(R.id.UserName_Textfield);
        password = findViewById(R.id.Password_Textfield);
        Signin_Button = findViewById(R.id.Signin_Button);
        Login_Button = findViewById(R.id.Logedin_Button);


        //functions
        Config_Click_Listener();
    }

    public void Config_Click_Listener() {
        Signin_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,
                        Signup.class));
            }
        });
        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate_Fields()) {
                    uname = name.getText().toString();
                    upassword = password.getText().toString();
                    LoginUser(uname, upassword);
                }
            }
        });
    }

    private void LoginUser(String name, String password) {
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.LoginUser(name, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (response != null) {
                    Log.i("data: ", response.toString());
                    //put User Data in shared preferences
                    if (response.body().isStatus()) {
                        myPreferences.setID(response.body().getId());
                        myPreferences.setUserName(response.body().getName());
                        myPreferences.setEmail(response.body().getEmail());
                        myPreferences.setToken(response.body().getToken());
                        myPreferences.setDay(response.body().getDay());
                        myPreferences.setInstitution(response.body().getInstitutename());
                        startActivity(new Intent(LogIn.this, Dashboard.class));
                    }else{
                        Toast.makeText(LogIn.this, "login " + response.body().getMessages(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }

    private Boolean Validate_Fields() {
        uname = name.getText().toString();
        upassword = password.getText().toString();
        if (uname.isEmpty()) {
            altertfail("User Name is required");
            return false;
        }
        if (upassword.isEmpty()) {
            altertfail("Password is required");
            return false;
        } else {
            return true;
        }

    }

    private void altertfail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Failed")
                .setIcon(R.drawable.warning_icon)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}