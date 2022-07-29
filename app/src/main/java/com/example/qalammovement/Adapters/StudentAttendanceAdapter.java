package com.example.qalammovement.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qalammovement.Activity.StudentAttendanceActivity;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.ReportResponse;
import com.example.qalammovement.Model.StudentModel;
import com.example.qalammovement.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAttendanceAdapter extends RecyclerView.Adapter<StudentAttendanceAdapter.MyViewHolder> {

    private List<StudentModel> list;
    private Activity activity;
    private int attendance_id;
    ProgressDialog progressDialog;

    public StudentAttendanceAdapter(List<StudentModel> list, Activity activity,int att_id) {
        this.list = list;
        this.activity = activity;
        this.attendance_id = att_id;
        progressDialog = new ProgressDialog(activity);
    }

    @NonNull @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_student_attendance_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(list.get(position).getName());
        holder.gender.setText(list.get(position).getGender());
        holder.contact.setText(list.get(position).getContact());
        holder.id.setText(list.get(position).getId()+"");
        holder.absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markAttendance(list.get(position).getId()+"",attendance_id+"",1);
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
            }
        });
        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markAttendance(list.get(position).getId()+"",attendance_id+"",0);
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
            }
        });

    }

    @Override public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,gender,contact,id;
        Button present,absent;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.student_name);
            gender = itemView.findViewById(R.id.gender);
            contact = itemView.findViewById(R.id.contact);
            id = itemView.findViewById(R.id.student_id);
            present = itemView.findViewById(R.id.btnpresent);
            absent = itemView.findViewById(R.id.btnabsent);
        }
    }

    private void markAttendance(String st_id, String att_id,int value){
        progressDialog.setTitle("please wait...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportResponse> call = apiInterface.markattendance(st_id,att_id,value);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                progressDialog.hide();
                Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(activity, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}

