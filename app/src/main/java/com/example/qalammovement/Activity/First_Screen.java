package com.example.qalammovement.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.qalammovement.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class First_Screen extends AppCompatActivity {

    AppCompatButton donate_now;
    AppCompatButton join_us;
    AppCompatButton login;
    AppCompatButton chatbot;
    //carousal images
     private int[] images=new int[]{
             R.drawable.pic1,R.drawable.pic2,R.drawable.pic3
     };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);

        //get components
        donate_now = findViewById(R.id.donate_now);
        join_us = findViewById(R.id.join_us);
        login= findViewById(R.id.login);
        chatbot= findViewById(R.id.chatbot);
        //function calls
        Config_ClicK_Listner();
        Display_Carousal();
    }

    private void Config_ClicK_Listner() {
        donate_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openwebsitedonationpage();
            }
        });
        join_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(First_Screen.this,
                                         Signup.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(First_Screen.this,
                                        LogIn.class));
            }
        });
        chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(First_Screen.this,
                        Chatbot.class));
            }
        });
    }

    private void openwebsitedonationpage() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://plutopakistan.com/fyp/public/qm/Donate.html"));
        startActivity(intent);
    }

    public  void Display_Carousal()
    {
        //carousal
        CarouselView carousal=findViewById(R.id.carousal);
        carousal.setPageCount(images.length);
        carousal.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(images[position]);
            }
        });
    }
}
