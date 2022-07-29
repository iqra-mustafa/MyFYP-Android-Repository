package com.example.qalammovement.Activity;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.School;
import com.example.qalammovement.Model.StudentResponse;
import com.example.qalammovement.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRegistration extends AppCompatActivity {
    BottomNavigationView nav;
    RadioGroup gender;
    RadioButton genderbtn1, genderbtn2;
    AwesomeValidation awesomeValidation;
    int radioid;
    ProgressDialog progressDialog;
    Spinner school_spinner, section_spinner;
    TextView dob;
    EditText name, religion, nationality, fathername, fathercontact, fathercnic,
            address, fatheroccupation, fatherincome;
    AppCompatButton registerbtn;
    String sname, sgender, sdob, sreligion, snationality, sfathername, sfathercontact, sfathercnic,
            saddress, sfatheroccupation, sfatherincome, sschoolname, ssection;
    private static List<String> schools = null;
    private List<School> schoolslist;
    private static final String[] classsection = {"A", "B", "C"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        nav = (BottomNavigationView) findViewById(R.id.Bottom_Nav);
        progressDialog = new ProgressDialog(this);
        name = (EditText) findViewById(R.id.Username_Textfield);
        genderbtn1 = (RadioButton) findViewById(R.id.male);
        genderbtn2 = (RadioButton) findViewById(R.id.female);
        dob = (TextView) findViewById(R.id.DOB_Textfield);
        religion = (EditText) findViewById(R.id.Religion_Textfield);
        nationality = (EditText) findViewById(R.id.Nationality_Textfield);
        fathername = (EditText) findViewById(R.id.FatherName_Textfield);
        fathercontact = (EditText) findViewById(R.id.Phoneno_Textfield);
        fathercnic = (EditText) findViewById(R.id.Cnic_Textfield);
        address = (EditText) findViewById(R.id.Address_Textfield);
        fatheroccupation = (EditText) findViewById(R.id.Occupation_Textfield);
        fatherincome = (EditText) findViewById(R.id.Income_Textfield);
        school_spinner = (Spinner) findViewById(R.id.School_Spinner);
        section_spinner = (Spinner) findViewById(R.id.Section_Spinner);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        registerbtn = (AppCompatButton) findViewById(R.id.Register_Student_Button);

        Validate_Fields();
        config_datepicker();
        config_button_click_listener();
        getSchools();
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_icon);
        }
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
                        Toast.makeText(StudentRegistration.this, "No Schools are added in the Database", Toast.LENGTH_SHORT).show();
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

    private void Validate_Fields() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(StudentRegistration.this, R.id.Username_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(StudentRegistration.this, R.id.DOB_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid_email);
        awesomeValidation.addValidation(StudentRegistration.this, R.id.FatherName_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(StudentRegistration.this, R.id.Phoneno_Textfield,
                "[0-9]{1}[0-9]{10}$", R.string.invalid_phoneno);
        awesomeValidation.addValidation(StudentRegistration.this, R.id.Cnic_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid_cnic);
        awesomeValidation.addValidation(StudentRegistration.this, R.id.Address_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid);
        awesomeValidation.addValidation(StudentRegistration.this, R.id.Occupation_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid);
        awesomeValidation.addValidation(StudentRegistration.this, R.id.Income_Textfield,
                RegexTemplate.NOT_EMPTY, R.string.invalid);
        awesomeValidation.addValidation(StudentRegistration.this, R.id.School_Spinner,
                RegexTemplate.NOT_EMPTY, R.string.invalid);
        awesomeValidation.addValidation(StudentRegistration.this, R.id.Section_Spinner,
                RegexTemplate.NOT_EMPTY, R.string.invalid);
        fathercnic.addTextChangedListener(new TextWatcher() {
            int len = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String str = fathercnic.getText().toString();
                len = str.length();
                if (len > 13) {
                    fathercnic.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = fathercnic.getText().toString();

                if ((str.length() == 5 && len < str.length()) || (str.length() == 13 && len < str.length())) {
                    //checking length  for backspace.
                    fathercnic.append("-");
                    //Toast.makeText(getBaseContext(), "add minus", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void config_button_click_listener() {

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    SaveData();
                }
            }
        });
    }

    private void SaveData() {
        sname = name.getText().toString();
        sdob = dob.getText().toString();
//        sgender = genderbtn.getText().toString();
        if (genderbtn2.isChecked()) {
            sgender = genderbtn2.getText().toString();
        } else {
            sgender = genderbtn1.getText().toString();
        }
        sreligion = religion.getText().toString();
        snationality = nationality.getText().toString();
        sfathername = fathername.getText().toString();
        sfathercontact = fathercontact.getText().toString();
        sfathercnic = fathercnic.getText().toString();
        sfatherincome = fatherincome.getText().toString();
        sfatheroccupation = fatheroccupation.getText().toString();
        saddress = address.getText().toString();
        sschoolname = schoolslist.get((int)school_spinner.getSelectedItemId()).getId()+"";
        ssection = section_spinner.getSelectedItem().toString();

        registerstudent(sname, sgender, sdob, ssection, sreligion, snationality, sschoolname, saddress, sfathercontact,
             sfathername, sfathercnic, sfatheroccupation, sfatherincome);
    }

    private void registerstudent(String sname, String sgender, String sdob, String ssection, String sreligion,
                              String snationality, String sschoolname, String saddress, String sfathercontact,
                              String sfathername, String sfathercnic, String sfatheroccupation, String sfatherincome) {

        progressDialog.setTitle("Please wait...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<StudentResponse> call = apiInterface.registerstudent(sname, sgender, sdob, ssection, sreligion, snationality, sschoolname,
                saddress, sfathercontact, sfathername, sfathercnic, sfatheroccupation, sfatherincome);
        call.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                Log.d("TAG", "StudentregisteronResponse: " + response.toString());
                progressDialog.dismiss();
                if (response != null) {
                    Log.i("data: ", response.toString());
                    if (response.body().isStatus()) {
                        Toast.makeText(StudentRegistration.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentRegistration.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }

    public void Config_Spinner_Item() {

        ArrayAdapter<String> school_adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, schools);
        ArrayAdapter<String> section_adapter = new ArrayAdapter<String>(StudentRegistration.this,
                R.layout.spinner_item, classsection);


        school_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        section_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        school_spinner.setAdapter(school_adapter);
        section_spinner.setAdapter(section_adapter);

    }

    private void config_datepicker() {
        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(StudentRegistration.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month = month + 1;
                                String date = dayOfMonth + "/" + month + "/" + year;
                                dob.setText(date);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
}