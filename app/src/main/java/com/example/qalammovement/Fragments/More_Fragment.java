package com.example.qalammovement.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qalammovement.Activity.Attandence;
import com.example.qalammovement.Activity.DailyReport;
import com.example.qalammovement.Activity.EmergencyActivity;
import com.example.qalammovement.Activity.ScheduleActivity;
import com.example.qalammovement.Activity.SchoolTeam;
import com.example.qalammovement.Activity.StationaryRequestActivity;
import com.example.qalammovement.Activity.StudentRegistration;
import com.example.qalammovement.R;

public class More_Fragment extends Fragment {
    @Nullable
    View view;
    Fragment selectedfragment = null;
    Toolbar mytoolbar;
    LinearLayout linear1 ,linear2,linear3,linear4,linear5,linear6,linear7,linear8;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_more,container,false);
        linear1=(LinearLayout) view.findViewById(R.id.linearLayout1);
        linear2=(LinearLayout) view.findViewById(R.id.linearLayout2);
        linear3=(LinearLayout) view.findViewById(R.id.linearLayout3);
        linear4=(LinearLayout) view.findViewById(R.id.linearLayout4);
        linear5=(LinearLayout) view.findViewById(R.id.linearLayout5);
        linear6=(LinearLayout) view.findViewById(R.id.linearLayout6);
        linear7=(LinearLayout) view.findViewById(R.id.linearLayout7) ;
        config_click_listener();
        return view;
    }

    private void config_click_listener() {
        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Attandence.class);
                startActivity(intent);
            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
            }
        });
        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EmergencyActivity.class));
            }
        });
        linear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SchoolTeam.class));
            }
        });
        linear5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DailyReport.class);
                startActivity(intent);

            }
        });
        linear6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StationaryRequestActivity.class));
            }
        });
        linear7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), StudentRegistration.class);
                startActivity(intent);

            }
        });
    }

}
