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
import com.example.qalammovement.Model.NotificationModel;
import com.example.qalammovement.Model.School;
import com.example.qalammovement.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<NotificationModel> list;
    private Activity activity;

    public NotificationAdapter(List<NotificationModel> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.roe_notification, parent, false);
        return new MyViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.description.setText(list.get(position).getDescription()+"");
        holder.date.setText(list.get(position).getCreated_at());
    }

    @Override public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,description,date;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }
    }

}

