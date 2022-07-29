package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qalammovement.Adapters.AttendanceListAdapter;
import com.example.qalammovement.Adapters.StudentList;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.AttandenceResponse;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.Model.StudentModel;
import com.example.qalammovement.Model.User;
import com.example.qalammovement.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Attandence extends AppCompatActivity {
    MyPreferences myPreferences;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    List<AttandenceResponse> list;
    AttendanceListAdapter adapter;
    Button createattendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandence);
        myPreferences = MyPreferences.getPreferences(Attandence.this);
        progressDialog = new ProgressDialog(this);
        recyclerView  = findViewById(R.id.recyclerview);
        createattendance = findViewById(R.id.createattendance);
        createattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Attandence.this,CreateAttendance.class));
            }
        });

        getAttendanceList();

    }

    private void getAttendanceList() {
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<AttandenceResponse>> call = apiInterface.getAllattendance(myPreferences.getID()+"");
        call.enqueue(new Callback<List<AttandenceResponse>>() {
            @Override
            public void onResponse(Call<List<AttandenceResponse>> call, Response<List<AttandenceResponse>> response) {
                progressDialog.hide();
                //Toast.makeText(StudentListActivity.this, "success", Toast.LENGTH_SHORT).show();
                list = response.body();
                if(list == null){
                    Toast.makeText(Attandence.this, "No Attendance data yet", Toast.LENGTH_SHORT).show();
                }else{
                    adapter = new AttendanceListAdapter(list,Attandence.this);
                    recyclerView.setAdapter(adapter);
                }
                
            }

            @Override
            public void onFailure(Call<List<AttandenceResponse>> call, Throwable t) {
                progressDialog.hide();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }
}