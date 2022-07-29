package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.qalammovement.Adapters.SchoolList;
import com.example.qalammovement.Adapters.StudentList;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.Model.School;
import com.example.qalammovement.Model.StudentModel;
import com.example.qalammovement.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    MyPreferences preferences;
    SchoolList schoolList;
    List<School> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);
        progressDialog = new ProgressDialog(this);
        preferences = MyPreferences.getPreferences(this);
        recyclerView  = findViewById(R.id.recyclerview);

        getStudentList();
    }

    private void getStudentList() {
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<School>> call = apiInterface.getSchools();
        call.enqueue(new Callback<List<School>>() {
            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                progressDialog.hide();
                //Toast.makeText(StudentListActivity.this, "success", Toast.LENGTH_SHORT).show();
                list = response.body();
                schoolList = new SchoolList(list,SchoolListActivity.this);
                recyclerView.setAdapter(schoolList);
            }

            @Override
            public void onFailure(Call<List<School>> call, Throwable t) {
                progressDialog.hide();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }
}