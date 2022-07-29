package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qalammovement.Adapters.ScheduleAdapter;
import com.example.qalammovement.Adapters.SchoolList;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.LoginResponse;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.Model.ReportResponse;
import com.example.qalammovement.Model.Schedule;
import com.example.qalammovement.Model.School;
import com.example.qalammovement.R;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    MyPreferences preferences;
    EditText time,subject;
    TextView mydate;
    AppCompatButton btnadd;

    RecyclerView recyclerView;
    List<Schedule> list;
    ScheduleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this);
        preferences = MyPreferences.getPreferences(this);
        getUserRole();
        super.onCreate(savedInstanceState);
    }

    private void getUserRole() {
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportResponse> call = apiInterface.isprincipal(preferences.getID());
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                progressDialog.dismiss();
                if(response.body().isStatus()){
                    setContentView(R.layout.activity_schedule);
                    addSchedule();
                }else{
                    setContentView(R.layout.activity_schedule_view);
                    viewShcedule();
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }

    private void addSchedule() {
        time = findViewById(R.id.time);
        subject = findViewById(R.id.subject);
        mydate = findViewById(R.id.Date_Textfield);
        btnadd = findViewById(R.id.btnadd);
        config_datepicker();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time.getText().toString().isEmpty() || subject.getText().toString().isEmpty()){
                    Toast.makeText(ScheduleActivity.this, "please fill all data", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mydate.getText().toString().equals("Select Date")){
                    Toast.makeText(ScheduleActivity.this, "Select a Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                uploadSchedule();
            }
        });




    }

    private void uploadSchedule() {
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportResponse> call = apiInterface.addScehdule(preferences.getInstitution()+"",mydate.getText().toString(),time.getText().toString(),subject.getText().toString());
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                Log.d("TAG", "signuponResponse: " + response.toString());
                progressDialog.dismiss();
                if (response != null) {
                    Log.i("data: ", response.toString());
                    if (response.body().isStatus()) {

                        Toast.makeText(ScheduleActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        mydate.setText("Select Date");
                        time.setText("");
                        subject.setText("");

                    } else {
                        Toast.makeText(ScheduleActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }

    private  void viewShcedule(){
        recyclerView  = findViewById(R.id.recyclerview);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Schedule>> call = apiInterface.getScehdule(preferences.getInstitution()+"");
        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                progressDialog.hide();
                //Toast.makeText(StudentListActivity.this, "success", Toast.LENGTH_SHORT).show();
                list = response.body();
                adapter = new ScheduleAdapter(list,ScheduleActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
                progressDialog.hide();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }

    private void config_datepicker() {
        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);
        mydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ScheduleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month = month + 1;
                                String date = dayOfMonth + "/" + month + "/" + year;
                                mydate.setText(date);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
}