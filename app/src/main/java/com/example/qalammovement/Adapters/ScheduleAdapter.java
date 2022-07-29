package com.example.qalammovement.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Model.Schedule;
import com.example.qalammovement.Model.School;
import com.example.qalammovement.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private List<Schedule> list;
    private Activity activity;

    public ScheduleAdapter(List<Schedule> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_schedule, parent, false);
        return new MyViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getSubject());
        holder.id.setText(list.get(position).getTime());
       // holder.gender.setText(list.get(position).getDate());
        holder.contact.setText(list.get(position).getDate());
    }

    @Override public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,gender,contact,id;
        CircleImageView profile_image;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.student_name);
            gender = itemView.findViewById(R.id.gender);
            contact = itemView.findViewById(R.id.contact);
            id = itemView.findViewById(R.id.student_id);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

}

