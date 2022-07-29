package com.example.qalammovement.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.qalammovement.Activity.Attandence;
import com.example.qalammovement.Activity.EmergencyActivity;
import com.example.qalammovement.Activity.ScheduleActivity;
import com.example.qalammovement.Activity.SchoolListActivity;
import com.example.qalammovement.Activity.StationaryRequestActivity;
import com.example.qalammovement.Activity.StudentListActivity;
import com.example.qalammovement.Activity.TeamListActivity;
import com.example.qalammovement.Activity.VolunteerListActivity;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.RegisteredStatisticsResponse;
import com.example.qalammovement.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Fragment extends Fragment {
    View view;
    ProgressDialog progressDialog;
    TextView registeredstudent,registeredschool,registeredvolunteer;
    String totalstudent,totalvolunteer,totalschool;
    LinearLayout student,voulnteer,school,teams,web;
    CardView card1,card2,card3,card4;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_home,container,false);
        card1=(CardView) view.findViewById(R.id.card1);
        card2=(CardView) view.findViewById(R.id.card2);
        card3=(CardView) view.findViewById(R.id.card3);
        card4=(CardView) view.findViewById(R.id.card4);
        web=(LinearLayout)  view.findViewById(R.id.Website);
        registeredstudent =(TextView)  view.findViewById(R.id.registered_student);
        registeredvolunteer =(TextView)  view.findViewById(R.id.registered_volunteer);
        registeredschool =(TextView)  view.findViewById(R.id.registered_institution);
        student = view.findViewById(R.id.student);
        voulnteer = view.findViewById(R.id.volunteer);
        school = view.findViewById(R.id.schools);
        teams = view.findViewById(R.id.teams);
        progressDialog = new ProgressDialog(getContext());
        getregisteredstatistics();

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StudentListActivity.class));
            }
        });
        voulnteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), VolunteerListActivity.class));
            }
        });
        teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TeamListActivity.class));
            }
        });
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SchoolListActivity.class));
            }
        });
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Attandence.class);
                startActivity(intent);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StationaryRequestActivity.class));
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EmergencyActivity.class));
            }
        });
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://plutopakistan.com/fyp/public/qm/"));
                startActivity(intent);
            }
        });
        return view;

    }

    private void getregisteredstatistics() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<RegisteredStatisticsResponse> call = apiInterface.getstatistics();
        call.enqueue(new Callback<RegisteredStatisticsResponse>() {
            @Override
            public void onResponse(Call<RegisteredStatisticsResponse> call, Response<RegisteredStatisticsResponse> response) {
                if (response != null) {
                    Log.i("data: ", response.toString());
                    //put User Data in shared preferences
                    if (response.body().isStatus()) {
                        totalschool=response.body().getRegistered_school();
                        totalstudent=response.body().getRegistered_student();
                        totalvolunteer=response.body().getRegistered_volunteer();
                        registeredstudent.setText(totalstudent);
                        registeredvolunteer.setText(totalvolunteer);
                        registeredschool.setText(totalschool);

                    }
                    }else{
                       // Toast.makeText(getContext(), "login " + response.body().getRegistered_volunteer(), Toast.LENGTH_SHORT).show();
                    }
                }

            @Override
            public void onFailure(Call<RegisteredStatisticsResponse> call, Throwable t) {
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }
}