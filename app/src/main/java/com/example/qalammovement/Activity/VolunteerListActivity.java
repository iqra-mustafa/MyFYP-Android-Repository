package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.qalammovement.Adapters.StudentList;
import com.example.qalammovement.Adapters.VolunteerList;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.Model.StudentModel;
import com.example.qalammovement.Model.VolunteerModel;
import com.example.qalammovement.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    MyPreferences preferences;
    VolunteerList volunteerList;
    List<VolunteerModel> list;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_list);
        progressDialog = new ProgressDialog(this);
        preferences = MyPreferences.getPreferences(this);
        recyclerView  = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);

        if(getIntent().hasExtra("team_name")){
            getVolunteerTeam();
            String title = getIntent().getStringExtra("team_name")+" Team";
            toolbar.setTitle(title);
        }else{
            getVolunteerList();
        }
    }

    private void getVolunteerTeam() {
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<VolunteerModel>> call = apiInterface.getTeams(getIntent().getStringExtra("team_name"));
        call.enqueue(new Callback<List<VolunteerModel>>() {
            @Override
            public void onResponse(Call<List<VolunteerModel>> call, Response<List<VolunteerModel>> response) {
                progressDialog.hide();
                list = response.body();
                volunteerList = new VolunteerList(list,VolunteerListActivity.this);
                recyclerView.setAdapter(volunteerList);
            }

            @Override
            public void onFailure(Call<List<VolunteerModel>> call, Throwable t) {
                progressDialog.hide();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }

    private void getVolunteerList() {
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<VolunteerModel>> call = apiInterface.getVolunteer(preferences.getInstitution());
        call.enqueue(new Callback<List<VolunteerModel>>() {
            @Override
            public void onResponse(Call<List<VolunteerModel>> call, Response<List<VolunteerModel>> response) {
                progressDialog.hide();
                list = response.body();
                volunteerList = new VolunteerList(list,VolunteerListActivity.this);
                recyclerView.setAdapter(volunteerList);
            }

            @Override
            public void onFailure(Call<List<VolunteerModel>> call, Throwable t) {
                progressDialog.hide();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }
}