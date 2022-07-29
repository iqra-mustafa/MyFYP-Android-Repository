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

public class CreateAttendance extends AppCompatActivity {

    AppCompatButton btncreate;
    EditText class_name,time;
    ProgressDialog progressDialog;
    MyPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_attendance);
        progressDialog = new ProgressDialog(this);
        preferences = MyPreferences.getPreferences(this);
        btncreate = findViewById(R.id.btncreate);
        class_name = findViewById(R.id.class_name);
        time = findViewById(R.id.time);

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time.getText().toString().isEmpty() || class_name.getText().toString().isEmpty()){
                    Toast.makeText(CreateAttendance.this, "please enter class name and time", Toast.LENGTH_SHORT).show();
                }else{
                    createAttendance();
                }
            }
        });
    }

    private void createAttendance() {
        progressDialog.setTitle("please wait...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportResponse> call = apiInterface.createAttendance(class_name.getText().toString(),time.getText().toString(),preferences.getID()+"",preferences.getInstitution()+"");
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                Log.d("TAG", "signuponResponse: " + response.toString());
                progressDialog.dismiss();
                if (response != null) {
                    Log.i("data: ", response.toString());
                    if (response.body().isStatus()) {
                        new AlertDialog.Builder(CreateAttendance.this)
                                .setTitle("Success")
                                .setMessage(response.body().getMessage()+".\n")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).show();
                    } else {
                        Toast.makeText(CreateAttendance.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CreateAttendance.this, "An error occured, please try again", Toast.LENGTH_SHORT).show();
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