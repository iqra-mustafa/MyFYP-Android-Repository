package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.qalammovement.R;

public class TeamListActivity extends AppCompatActivity {

    CardView teaching,hr,videophoto,socialmedia,contentwriting,graphicdesigning;
    String value;
    private static final String[] teams = {"Teaching", "HR", "Video or Photography", "Social Media", "Content Writing", "Graphic Designing"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        teaching = findViewById(R.id.teaching);
        hr = findViewById(R.id.hr);
        videophoto = findViewById(R.id.videoandphotography);
        socialmedia = findViewById(R.id.socialmedia);
        contentwriting = findViewById(R.id.contentwriting);
        graphicdesigning = findViewById(R.id.graphicdesigning);
        teaching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "Teaching";
                Intent intent = new Intent(TeamListActivity.this,VolunteerListActivity.class);
                intent.putExtra("team_name",value);
                startActivity(intent);
            }
        });
        hr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "HR";
                Intent intent = new Intent(TeamListActivity.this,VolunteerListActivity.class);
                intent.putExtra("team_name",value);
                startActivity(intent);
            }
        });
        videophoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "Video or Photography";
                Intent intent = new Intent(TeamListActivity.this,VolunteerListActivity.class);
                intent.putExtra("team_name",value);
                startActivity(intent);
            }
        });
        socialmedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "Social Media";
                Intent intent = new Intent(TeamListActivity.this,VolunteerListActivity.class);
                intent.putExtra("team_name",value);
                startActivity(intent);
            }
        });
        contentwriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "Content Writing";
                Intent intent = new Intent(TeamListActivity.this,VolunteerListActivity.class);
                intent.putExtra("team_name",value);
                startActivity(intent);
            }
        });
        graphicdesigning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "Graphic Designing";
                Intent intent = new Intent(TeamListActivity.this,VolunteerListActivity.class);
                intent.putExtra("team_name",value);
                startActivity(intent);
            }
        });
    }
}