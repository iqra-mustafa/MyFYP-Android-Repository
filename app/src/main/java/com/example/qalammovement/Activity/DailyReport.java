package com.example.qalammovement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.Model.PermissionsChecker;
import com.example.qalammovement.Model.ReportResponse;
import com.example.qalammovement.R;

import java.io.File;
import java.util.Calendar;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyReport extends AppCompatActivity {
    EditText studentno, feedback;
    TextView mydate;
    AppCompatButton done_button, uploadpic;
    String rdate, rnoofstudent, rfeedback;
    ProgressDialog progressDialog;
    DatePickerDialog.OnDateSetListener setListener;
    String imagePath;
    MultipartBody.Part body;
    PermissionsChecker checker;
    ImageView imagview;
    MyPreferences preferences;
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);
        studentno = (EditText) findViewById(R.id.noofstudent);
        mydate = (TextView) findViewById(R.id.Date_Textfield);
        feedback = (EditText)findViewById(R.id.Feedback_Textfield);
        done_button = (AppCompatButton) findViewById(R.id.Done_Button);
        uploadpic = (AppCompatButton) findViewById(R.id.upload_Button);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        imagview = findViewById(R.id.imagview);
        progressDialog = new ProgressDialog(this);
        checker = new PermissionsChecker(this);
        preferences = MyPreferences.getPreferences(this);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_icon);
        }

        config_datepicker();
        config_button_click();
        uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDailyreport();
            }
        });
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

    private void config_button_click() {
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdate = mydate.getText().toString();
                rnoofstudent = studentno.getText().toString();
                rfeedback = feedback.getText().toString();
                if (validatefrom(rdate, rnoofstudent, rfeedback)) {
                    Toast.makeText(DailyReport.this,"registered", Toast.LENGTH_SHORT).show();
                    sendreport(rdate, rnoofstudent, rfeedback);
                }
            }
        });
    }

    private boolean validatefrom(String rdate, String rnoofstudent, String rfeedback) {
        if (rdate.isEmpty()) {
            altertfail("Please Select Date");
            return false;
        }
        if (rnoofstudent.isEmpty()) {
            altertfail("Enter Strength of student");
            return false;
        }
        if (rfeedback.isEmpty()) {
            altertfail("Must Fill Feedback");
            return false;
        } else {
            return true;
        }

    }

    private void altertfail(String s) {
        new AlertDialog.Builder(DailyReport.this)
                .setTitle("Failed")
                .setIcon(R.drawable.warning_icon)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
    private void uploadDailyreport() {
        if(studentno.getText().equals("") || feedback.getText().equals("")){
            Toast.makeText(this, "No of Student & Feedback is necessary", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mydate.getText().toString().equals("Select Date")){
            Toast.makeText(this, "Select a Date", Toast.LENGTH_SHORT).show();
            return;
        }
        if(imagePath == null){
            Toast.makeText(this, "Select an Image to upload", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
        File file = new File(imagePath);
        File file2 = Compressor.getDefault(this).compressToFile(file);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        if(body == null){
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody noofstudent = RequestBody.create(MediaType.parse("text/plain"),
                studentno.getText().toString());
        RequestBody feddback_text = RequestBody.create(MediaType.parse("text/plain"),
                feedback.getText().toString());
        RequestBody school_id = RequestBody.create(MediaType.parse("text/plain"),
                preferences.getInstitution()+"");


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportResponse> call = apiInterface.addDailyReport(noofstudent,feddback_text,school_id,body);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                Log.d("TAG", "signuponResponse: " + response.toString());
                progressDialog.dismiss();
                if (response != null) {
                    Log.i("data: ", response.toString());
                    if (response.body().isStatus()) {
                        new AlertDialog.Builder(DailyReport.this)
                                .setTitle("Success")
                                .setMessage(response.body().getMessage())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).show();
                    } else {
                        new AlertDialog.Builder(DailyReport.this)
                                .setTitle("Success")
                                .setMessage(response.body().getMessage())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).show();
                    }
                }else{
                    Toast.makeText(DailyReport.this, "An error occured, please try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }

    private void sendreport(String thisdate, String thisnoofstudent, String thisfeedback) {
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportResponse> call = apiInterface.report(thisdate, thisnoofstudent, thisfeedback);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                Log.d("TAG", "signuponResponse: " + response.toString());
                progressDialog.dismiss();
                if (response != null) {
                    Log.i("data: ", response.toString());
                    if (response.body().isStatus()) {

                        Toast.makeText(DailyReport.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(DailyReport.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }

    private void config_datepicker() {
        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);
        mydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(DailyReport.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month = month + 1;
                                String date = dayOfMonth + "/" + month + "/" + year;
                                mydate.setText(date);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
    private void openGallery() {
        if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        } else {
            // File System.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_PICK);

            // Chooser of file system options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, "Choose Image");
            startActivityForResult(chooserIntent, 1010);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1010) {
            if (data == null) {
                //  Snackbar.make(scrollView, "Unable to Pick Image", Snackbar.LENGTH_INDEFINITE).show();
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                File file = new File(imagePath);
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imagview.setImageBitmap(myBitmap);
                imagview.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
                cursor.close();
            } else {
                // Snackbar.make(scrollView, "Image Not Loading, Try Again", Snackbar.LENGTH_LONG).show();
            }
        }
    }
    private void startPermissionsActivity(String[] permission) {
        PermissionsActivity.startActivityForResult(this, 0, permission);
    }
}

