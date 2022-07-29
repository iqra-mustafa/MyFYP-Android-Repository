package com.example.qalammovement.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import com.example.qalammovement.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Location extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    Marker mMarker = null;
    Spinner school_spinner;
    Toolbar toolbar;
    private static final String[] schools = {"Iqra Abadi", "Afghan Abadi", "School No 3", "School No 2", "School No 1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_location);
        school_spinner = findViewById(R.id.School_Spinner);
        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        // add back arrow to toolbar
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_icon);
//        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
        Double lan = 3.666080;
        Double log = 72.996544;
        Config_Spinner();

    }

    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notification_meun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void onMapReady(@NonNull GoogleMap googleMap) {

        school_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (mMarker != null)
                            mMarker.remove();
                        mMap = googleMap;
                        LatLng school1 = new LatLng(33.658795, 73.038156);
                        mMarker = mMap.addMarker(new MarkerOptions().position(school1).title("Iqra Abadi"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(school1));

                        break;
                    case 1:
                        if (mMarker != null)
                            mMarker.remove();
                        mMap = googleMap;
                        LatLng school2 = new LatLng(33.75289, 73.442781);
                        mMarker = mMap.addMarker(new MarkerOptions().position(school2).title("Afghan Abadi"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(school2));

                        break;
                    case 2:
                        if (mMarker != null)
                            mMarker.remove();
                        mMap = googleMap;
                        LatLng school3 = new LatLng(33.75289, 73.442781);
                        mMarker = mMap.addMarker(new MarkerOptions().position(school3).title("School 3"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(school3));

                        break;
                    case 3:
                        if (mMarker != null)
                            mMarker.remove();
                        mMap = googleMap;
                        LatLng school4 = new LatLng(33.75289, 73.442781);
                        mMarker = mMap.addMarker(new MarkerOptions().position(school4).title("School 2"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(school4));

                        break;
                    case 4:
                        if (mMarker != null)
                            mMarker.remove();
                        mMap = googleMap;
                        LatLng school5 = new LatLng(33.75289, 73.442781);
                        mMarker = mMap.addMarker(new MarkerOptions().position(school5).title("School 1"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(school5));

                        break;
                    case 5:
                        if (mMarker != null)
                            mMarker.remove();
                        mMap = googleMap;
                        LatLng school6 = new LatLng(33.75289, 73.442781);
                        mMarker = mMap.addMarker(new MarkerOptions().position(school6).title("School 6"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(school6));

                        break;
                    default:
                        mMap = googleMap;

                        // Add a marker in Sydney and move the camera
                        LatLng sydney1 = new LatLng(33.666080, 72.996544);
                        mMap.addMarker(new MarkerOptions().position(sydney1).title("Qalam Movement"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney1));
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void config_location(Double lan, Double log) {
        onMapReady(this.mMap);
    }


    private void Config_Spinner() {
        ArrayAdapter<String> school_adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, schools);
        school_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        school_spinner.setAdapter(school_adapter);
        school_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:


                        break;
                    case 1:


                        break;
                    case 2:


                        break;
                    case 3:


                        break;
                    case 4:


                        break;
                    case 5:


                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
