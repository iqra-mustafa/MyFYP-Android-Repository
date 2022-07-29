package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.qalammovement.R;

public class Full_ScreenImage extends AppCompatActivity {
ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        image=findViewById(R.id.fullimage);
        int img_id=getIntent().getExtras().getInt("img_id");
        image.setImageResource(img_id);
    }
}