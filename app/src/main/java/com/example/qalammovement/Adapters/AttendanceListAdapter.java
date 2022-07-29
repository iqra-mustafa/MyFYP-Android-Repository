package com.example.qalammovement.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qalammovement.Activity.StudentAttendanceActivity;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Model.AttandenceResponse;
import com.example.qalammovement.Model.School;
import com.example.qalammovement.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.MyViewHolder> {

    private List<AttandenceResponse> list;
    private Activity activity;

    public AttendanceListAdapter(List<AttandenceResponse> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_attendance, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.class_name.setText(list.get(position).getUserclass());
        holder.date.setText(list.get(position).getDate()+"\nMarked Student:  "+list.get(position).getCount());

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(list.get(position).getCount() >= 3){
                        Toast.makeText(activity, "Attendance Marked, Submitted to Admin", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(activity, StudentAttendanceActivity.class);
                        intent.putExtra("school_id",list.get(position).getSchool_id());
                        intent.putExtra("attendance_id",list.get(position).getId());
                        activity.startActivity(intent);
                    }
                }
            }); 

     

    }

    @Override public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView class_name,date;
        CardView card;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            class_name = itemView.findViewById(R.id.class_name);
            date = itemView.findViewById(R.id.date);
            card = itemView.findViewById(R.id.card);
        }
    }

}

