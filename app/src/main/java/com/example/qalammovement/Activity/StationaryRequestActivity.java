package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.Model.ReportResponse;
import com.example.qalammovement.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationaryRequestActivity extends AppCompatActivity {

    EditText stationary_description;
    ProgressDialog progressDialog;
    MyPreferences preferences;
    AppCompatButton btndone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationary_request);
        stationary_description = findViewById(R.id.stationary_description);
        progressDialog = new ProgressDialog(this);
        preferences = MyPreferences.getPreferences(this);
        btndone = findViewById(R.id.btndone);
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStationary();
            }
        });

    }

    private void requestStationary() {
        progressDialog.setTitle("Please wait...");
        if(stationary_description.getText().toString().isEmpty() || stationary_description.getText().toString().length() < 10){
            Toast.makeText(this, "Please enter proper description", Toast.LENGTH_SHORT).show();
        }
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportResponse> call = apiInterface.requestStationary(preferences.getInstitution()+"",preferences.getID()+"",stationary_description.getText().toString());
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                Log.d("TAG", "signuponResponse: " + response.toString());
                progressDialog.dismiss();
                if (response != null) {
                    Log.i("data: ", response.toString());
                    if (response.body().isStatus()) {
                        new AlertDialog.Builder(StationaryRequestActivity.this)
                                .setTitle("Success")
                                .setCancelable(false)
                                .setMessage(response.body().getMessage()+".\nAdmin will response you back via email or your contact number.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).show();
                    } else {
                        Toast.makeText(StationaryRequestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(StationaryRequestActivity.this, "An error occured, please try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }
}