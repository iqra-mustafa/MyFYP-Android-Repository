package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.qalammovement.Adapters.StudentList;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.Model.RegisteredStatisticsResponse;
import com.example.qalammovement.Model.StudentModel;
import com.example.qalammovement.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    MyPreferences preferences;
    StudentList studentList;
    List<StudentModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        progressDialog = new ProgressDialog(this);
        preferences = MyPreferences.getPreferences(this);
        recyclerView  = findViewById(R.id.recyclerview);

        getStudentList();

    }

    private void getStudentList() {
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<StudentModel>> call = apiInterface.getAllStudents();
        call.enqueue(new Callback<List<StudentModel>>() {
            @Override
            public void onResponse(Call<List<StudentModel>> call, Response<List<StudentModel>> response) {
                progressDialog.hide();
                //Toast.makeText(StudentListActivity.this, "success", Toast.LENGTH_SHORT).show();
                list = response.body();
                studentList = new StudentList(list,StudentListActivity.this);
                recyclerView.setAdapter(studentList);
            }

            @Override
            public void onFailure(Call<List<StudentModel>> call, Throwable t) {
                progressDialog.hide();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }
}