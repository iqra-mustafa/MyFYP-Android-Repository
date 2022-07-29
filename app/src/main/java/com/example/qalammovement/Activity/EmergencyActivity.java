package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.qalammovement.R;

public class EmergencyActivity extends AppCompatActivity {

    CardView emergency,transport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        emergency = findViewById(R.id.emergency);
        transport = findViewById(R.id.transport);
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyActivity.this,EmergencyRequestActivity.class);
                intent.putExtra("request_type",1);
                startActivity(intent);
            }
        });
        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyActivity.this,EmergencyRequestActivity.class);
                intent.putExtra("request_type",0);
                startActivity(intent);
            }
        });
    }
}