package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.R;

public class SplashScreen extends AppCompatActivity {

     //variables
    private static int SPLASH_SCREEN=3000;
    Animation topanim , bottomanim;
    ImageView img;
    TextView logo, slogan;
    MyPreferences myPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        myPreferences = MyPreferences.getPreferences(this);

        //functions
        Animation_Splashsreen();
    }

    public void Animation_Splashsreen(){
        //animation call
        topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        img=findViewById(R.id.imageView);
        logo=findViewById(R.id.textView);
        slogan=findViewById(R.id.textView2);

        img.setAnimation(topanim);
        logo.setAnimation(bottomanim);
        slogan.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(myPreferences.getEmail() != null){
                    Intent intent=new Intent(SplashScreen.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent=new Intent(SplashScreen.this, First_Screen.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_SCREEN);
    }
}