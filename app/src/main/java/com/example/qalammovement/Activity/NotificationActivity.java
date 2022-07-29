package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.qalammovement.Adapters.NotificationAdapter;
import com.example.qalammovement.Adapters.StudentList;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.Model.NotificationModel;
import com.example.qalammovement.Model.StudentModel;
import com.example.qalammovement.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    MyPreferences preferences;
    NotificationAdapter adapter;
    List<NotificationModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        progressDialog = new ProgressDialog(this);
        preferences = MyPreferences.getPreferences(this);
        recyclerView  = findViewById(R.id.recyclerview);

        getNotifications();
    }
    private void getNotifications() {
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<NotificationModel>> call = apiInterface.getNotification();
        call.enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                progressDialog.hide();
                //Toast.makeText(StudentListActivity.this, "success", Toast.LENGTH_SHORT).show();
                list = response.body();
                adapter = new NotificationAdapter(list,NotificationActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                progressDialog.hide();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }
}