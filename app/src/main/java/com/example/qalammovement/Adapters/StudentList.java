package com.example.qalammovement.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qalammovement.Model.Message;
import com.example.qalammovement.Model.StudentModel;
import com.example.qalammovement.R;

import java.util.List;

public class StudentList extends RecyclerView.Adapter<StudentList.MyViewHolder> {

    private List<StudentModel> list;
    private Activity activity;

    public StudentList(List<StudentModel> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_student_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.gender.setText(list.get(position).getGender());
        holder.contact.setText(list.get(position).getContact());
        holder.id.setText(list.get(position).getId()+"");

    }

    @Override public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,gender,contact,id;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.student_name);
            gender = itemView.findViewById(R.id.gender);
            contact = itemView.findViewById(R.id.contact);
            id = itemView.findViewById(R.id.student_id);
        }
    }

}

