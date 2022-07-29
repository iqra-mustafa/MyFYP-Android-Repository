package com.example.qalammovement.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.bumptech.glide.Glide;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.Model.School;
import com.example.qalammovement.Model.User;
import com.example.qalammovement.R;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {
    EditText name, email, number, cnic, address, uni_name, degree, semester, password, confirmpassword;
    String uname;
    String uemail;
    String unumber;
    String ucnic;
    String uaddress;
    String uuni_name;
    String udegree;
    String usemester;
    String upassword;
    String uteamname;
    String uday;
    int uinstitutename;
    AppCompatButton signin, login;
    Spinner day_spinner, school_spinner, team_spinner;
    ImageView image;
    TextView school_location, school_timming;
    ProgressDialog progressDialog;
    AwesomeValidation awesomeValidation;
    boolean passwordvisibility;
    private static final String[] teams = {"Teaching", "HR", "Video or Photography", "Social Media", "Content Writing", "Graphic Designing"};
    private static List<String> schools = null;
    private List<School> schoolslist;

    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        myPreferences = MyPreferences.getPreferences(Signup.this);
        progressDialog = new ProgressDialog(this);
        name = findViewById(R.id.Username_Textfield);
        email = findViewById(R.id.Email_Textfield);
        number = findViewById(R.id.Phoneno_Textfield);
        cnic = findViewById(R.id.Cnic_Textfield);
        address = findViewById(R.id.Address_Textfield);
        uni_name = findViewById(R.id.UniversityName_Textfield);
        degree = findViewById(R.id.Degree_Textfield);
        semester = findViewById(R.id.Semester_Textfield);
        password = findViewById(R.id.Password_Textfield);
        confirmpassword = findViewById(R.id.Reenterpassword_Textfield);
        signin = findViewById(R.id.Signin_Button);
        login = findViewById(R.id.Login_Button);
        team_spinner = findViewById(R.id.Team_Spinner);
        school_spinner = findViewById(R.id.School_Spinner);
        day_spinner = findViewById(R.id.Day_Spinner);
        image = findViewById(R.id.School_Image);
        school_location = findViewById(R.id.Image_location);
        school_timming = findViewById(R.id.school_timing);
        getSchools();
        //functions
        Validate_Fields();
        Config_Click_Listener();

    }



    private void Validate_Fields() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.Username_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.Phoneno_Textfield,
                "[0-9]{1}[0-9]{10}$", R.string.invalid_phoneno);
        awesomeValidation.addValidation(this, R.id.Email_Textfield,
                Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.Cnic_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid_cnic);
        awesomeValidation.addValidation(this, R.id.Password_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.Reenterpassword_Textfield,
                R.id.Password_Textfield, R.string.invalid_reenter_password);
        awesomeValidation.addValidation(this, R.id.UniversityName_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid_Uni);
        awesomeValidation.addValidation(this, R.id.Degree_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid_degree);
        awesomeValidation.addValidation(this, R.id.Semester_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid_semester);
        awesomeValidation.addValidation(this, R.id.Team_Spinner,
                RegexTemplate.NOT_EMPTY, R.string.invalid_team);
        awesomeValidation.addValidation(this, R.id.Day_Spinner,
                RegexTemplate.NOT_EMPTY, R.string.invalid_day);
        awesomeValidation.addValidation(this, R.id.School_Spinner,
                RegexTemplate.NOT_EMPTY, R.string.invalid_school);
        cnic.addTextChangedListener(new TextWatcher() {
            int len = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String str = cnic.getText().toString();
                len = str.length();
                if (len > 13) {
                    cnic.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = cnic.getText().toString();

                if ((str.length() == 5 && len < str.length()) || (str.length() == 13 && len < str.length())) {
                    //checking length  for backspace.
                    cnic.append("-");
                    //Toast.makeText(getBaseContext(), "add minus", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void Config_Click_Listener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,
                        LogIn.class));

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                SaveData();
                 }
            }
        });
    }

    private void SaveData() {
        uname = name.getText().toString();
        uemail = email.getText().toString();
        unumber = number.getText().toString();
        ucnic = cnic.getText().toString();
        uaddress = address.getText().toString();
        uuni_name = uni_name.getText().toString();
        udegree = degree.getText().toString();
        usemester = semester.getText().toString();
        uteamname = team_spinner.getSelectedItem().toString();
        uday = day_spinner.getSelectedItem().toString();
        uinstitutename = (int) school_spinner.getSelectedItemId();
        upassword = password.getText().toString();
        registerUser(uname, uemail, unumber, ucnic, uaddress, uuni_name, udegree, usemester, uteamname, uday, uinstitutename, upassword);
    }

    private void registerUser(String name, String email, String contactnumber,
                              String cnic, String address, String universityname, String degree,
                              String semester, String teamname, String day, int institutename, String password) {
        Toast.makeText(this,  schoolslist.get(institutename).getId()+"", Toast.LENGTH_SHORT).show();
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String school_id = schoolslist.get(institutename).getId()+"";
        Call<User> call = apiInterface.registerUser(name, email, contactnumber, cnic, address, universityname,
                degree, semester, teamname, day, school_id , password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG", "signuponResponse: " + response.toString());
                progressDialog.dismiss();
                if (response != null) {
                    Log.i("data: ", response.toString());
                    if (response.body().isStatus()) {
                        myPreferences.setID(response.body().getId());
                        myPreferences.setUserName(response.body().getName());
                        myPreferences.setEmail(response.body().getEmail());
                        myPreferences.setToken(response.body().getToken());
                        myPreferences.setDay(response.body().getDay());
                        myPreferences.setInstitution(response.body().getInstitutename());

                        startActivity(new Intent(Signup.this,LogIn.class));
                        finish();
                        Toast.makeText(Signup.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Signup.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }
    private void getSchools() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<School>> call = apiInterface.getSchools();
        call.enqueue(new Callback<List<School>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                Log.d("TAG", "schoolresponse: " + response.toString());
                progressDialog.dismiss();
                if (response != null) {
                    Log.i("data: ", response.toString());
                    if(response.body().size() <= 0){
                        Toast.makeText(Signup.this, "No Schools are added in the Database", Toast.LENGTH_SHORT).show();
                    }else{
                        schoolslist = response.body();
                        schools = response.body().stream().map(School::getName).collect(Collectors.toList());
                       // schoolsIds = response.body().stream().map(School::getId).collect(Collectors.toList());
                        Config_Spinner_Item();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<School>> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });


    }
    public void Config_Spinner_Item() {

        ArrayAdapter<String> school_adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, schools);
        ArrayAdapter<String> team_adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, teams);
        ArrayAdapter<String> day_adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, days);

        school_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        team_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        day_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        school_spinner.setAdapter(school_adapter);
        team_spinner.setAdapter(team_adapter);
        day_spinner.setAdapter(day_adapter);

        school_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Glide.with(Signup.this)
                        .load(ApiClient.BASE_URL_SCHOOL_IMAGES+schoolslist.get(position).getImage())
                        .placeholder(R.drawable.imageloading)
                        .into(image);
                school_location.setText(schoolslist.get(position).getLocation());
                school_timming.setText(schoolslist.get(position).getTiming());

                /*switch (position) {



                    case 0:
                        image.setImageResource(R.drawable.school_2);

                        break;
                    case 1:
                        image.setImageResource(R.drawable.school_3);
                        school_location.setText(R.string.School_2_location);
                        school_timming.setText("2pm - 4pm");
                        break;
                    case 2:
                        image.setImageResource(R.drawable.school_1);
                        school_location.setText(R.string.School_3_location);
                        school_timming.setText("10m - 4pm");
                        break;
                    case 3:
                        image.setImageResource(R.drawable.school_4);
                        school_location.setText(R.string.School_4_location);
                        school_timming.setText("2pm - 4pm");
                        break;
                    case 4:
                        image.setImageResource(R.drawable.school_5);
                        school_location.setText(R.string.School_5_location);
                        school_timming.setText("2pm - 4pm");
                        break;
                    case 5:
                        image.setImageResource(R.drawable.school_6);
                        school_location.setText(R.string.School_6_location);
                        school_timming.setText("2pm - 4pm");
                        break;
                }*/
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}