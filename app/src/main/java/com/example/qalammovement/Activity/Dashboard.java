package com.example.qalammovement.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.qalammovement.Fragments.Gallary_Fragment;
import com.example.qalammovement.Fragments.Home_Fragment;
import com.example.qalammovement.Fragments.More_Fragment;
import com.example.qalammovement.Fragments.Terms_Conditions_Fragment;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.R;
import com.google.android.gms.maps.model.Dash;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Dashboard extends AppCompatActivity {
    BottomNavigationView nav;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigation;
    ActionBarDrawerToggle toogle;
    MyPreferences myPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        myPreferences = MyPreferences.getPreferences(Dashboard.this);
        drawer = findViewById(R.id.Drawer_layout);
        navigation = findViewById(R.id.Nav_View);
        toolbar = findViewById(R.id.toolbar);

        Config_Toolbar();
        Config_Bottom_Nav();
        Config_Drawer_Nav();
        requestPermission();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(Dashboard.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Dashboard.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
                    12);
        }
    }

    //Bottom_meun_item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notification_meun, menu);
        return true;
    }

    //toolbar
    public void Config_Toolbar() {
        setSupportActionBar(toolbar);
    }

    //botton_navigation
    public void Config_Bottom_Nav() {
        nav = findViewById(R.id.Bottom_Nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Container, new Home_Fragment()).commit();
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfragment = null;
                switch (item.getItemId()) {
                    case R.id.Home_Item:
                        selectedfragment = new Home_Fragment();
                        toolbar.setTitle("Qalam Movement");
                        Config_Drawer_Nav();
                        break;
                    case R.id.Gallary:
                        selectedfragment = new Gallary_Fragment();
                        toolbar.setTitle("Gallary");
                        Config_Drawer_Nav();
                        break;
                    case R.id.Location:
                        startActivity(new Intent(Dashboard.this,
                               Location.class));
                        break;
                    case R.id.More_Item:
                        selectedfragment = new More_Fragment();
                        toolbar.setTitle("More");
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Container, selectedfragment).commit();
                return true;
            }
        });
    }
    public static String readFromAssets(Context context, String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename), Charset.forName("UTF-16")));
        // do reading, usually loop until end of file reading
        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        while (mLine != null) {
            sb.append(mLine); // process line
            mLine = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }


    private void aboutus() {
        try {
            final Dialog dialog = new Dialog(Dashboard.this, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.privacypolicy);
            dialog.setCancelable(true);
            Button gotit = dialog.findViewById(R.id.gotIt);
            TextView textView = dialog.findViewById(R.id.textView);
            textView.setText(Html.fromHtml(readFromAssets(Dashboard.this, "about.txt")));

            gotit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showpolicy() {
        try {
            final Dialog dialog = new Dialog(Dashboard.this, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.privacypolicy);
            dialog.setCancelable(true);
            Button gotit = dialog.findViewById(R.id.gotIt);
            TextView textView = dialog.findViewById(R.id.textView);
            textView.setText(Html.fromHtml(readFromAssets(Dashboard.this, "html.txt")));

            gotit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Drawer_navigation
    public void Config_Drawer_Nav() {
        navigation.bringToFront();
        toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toogle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.medium_yellow));
        drawer.addDrawerListener(toogle);
        toogle.syncState();
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedfragment = null;
                switch (item.getItemId()) {
                    case R.id.Help:
                        startActivity(new Intent(Dashboard.this,
                                Chatbot.class));
                        break;
                    case R.id.Aboutus:
                        aboutus();
                        break;
                    case R.id.Complain:
                        startActivity(new Intent(Dashboard.this,AddComplaint.class));
                        break;
                    case R.id.Term_Conditions:
                        showpolicy();
                        break;
                    case R.id.Logout:
                       myPreferences.logout();
                     startActivity(new Intent(Dashboard.this,First_Screen.class));
                     break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    //Back_press during Drawer navigation
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
          else if(nav.getSelectedItemId()==R.id.Home_Item){
              super.onBackPressed();
              finish();
        }
          else{
              nav.setSelectedItemId(R.id.Home_Item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                startActivity(new Intent(this,NotificationActivity.class));
                return true;
            case R.id.refresh:
                finish();
                startActivity(getIntent());
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}