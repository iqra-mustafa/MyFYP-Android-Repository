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
import android.widget.TextView;
import android.widget.Toast;

import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.Model.ReportResponse;
import com.example.qalammovement.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmergencyRequestActivity extends AppCompatActivity {

    EditText title,description;
    int requesttype;
    AppCompatButton btnsendrequest;
    MyPreferences preferences;
    ProgressDialog progressDialog;
    TextView requesttypetv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_request);
        progressDialog = new ProgressDialog(this);
        requesttypetv = findViewById(R.id.requesttype);
        requesttype = getIntent().getIntExtra("request_type",0);
        if(requesttype == 0){
            requesttypetv.setText("Trasnport Request");
        }else{
            requesttypetv.setText("Emergency Request");
        }
        preferences = MyPreferences.getPreferences(this);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        btnsendrequest = findViewById(R.id.btnsendrequest);
        btnsendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("please wait...");
                sendEmergencyRequest();
            }
        });
    }

    private void sendEmergencyRequest() {
        if(title.getText().toString().isEmpty() || description.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter Title & Description", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportResponse> call = apiInterface.emergencyRequest(title.getText().toString(),preferences.getID()+"",description.getText().toString(),requesttype);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                Log.d("TAG", "signuponResponse: " + response.toString());
                progressDialog.dismiss();
                if (response != null) {
                    Log.i("data: ", response.toString());
                    if (response.body().isStatus()) {
                        new AlertDialog.Builder(EmergencyRequestActivity.this)
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
                        Toast.makeText(EmergencyRequestActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EmergencyRequestActivity.this, "An error occured, please try again", Toast.LENGTH_SHORT).show();
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