package com.example.qalammovement.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.MyPreferences;
import com.example.qalammovement.Model.PermissionsChecker;
import com.example.qalammovement.Model.ReportResponse;
import com.example.qalammovement.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddComplaint extends AppCompatActivity {

    AppCompatButton button_send,button_upload;
    TextView status_msg_img;
    EditText title,description;
    public static final int PICK_IMAGE = 12;
    MultipartBody.Part body;
    ProgressDialog progressDialog;
    MyPreferences preferences;
    RelativeLayout scrollView;
    String imagePath;
    PermissionsChecker checker;
    ImageView imagview;
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        preferences = MyPreferences.getPreferences(this);
        checker = new PermissionsChecker(this);

        setContentView(R.layout.activity_add_complaint);

        scrollView = findViewById(R.id.scrollview);
        imagview = findViewById(R.id.imagview);
        button_send = findViewById(R.id.Done_Button);
        button_upload = findViewById(R.id.upload_Button);
        status_msg_img = findViewById(R.id.status_msg);
        title = findViewById(R.id.complaint_title);
        description = findViewById(R.id.complaint_description);


        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComplaint();
            }
        });
        button_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    openGallery();
            }
        });
    }

    private void addComplaint() {
        if(description.getText().equals("") || title.getText().equals("")){
            Toast.makeText(this, "Decription & Title is necessary", Toast.LENGTH_SHORT).show();
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
        RequestBody title_text = RequestBody.create(MediaType.parse("text/plain"),
                title.getText().toString());
        RequestBody description_text = RequestBody.create(MediaType.parse("text/plain"),
                description.getText().toString());
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"),
                preferences.getID()+"");
        RequestBody school_id = RequestBody.create(MediaType.parse("text/plain"),
                preferences.getInstitution()+"");


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ReportResponse> call = apiInterface.addComplaint(title_text,description_text,user_id,school_id,body);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                Log.d("TAG", "signuponResponse: " + response.toString());
                progressDialog.dismiss();
                if (response != null) {
                    Log.i("data: ", response.toString());
                    if (response.body().isStatus()) {
                        new AlertDialog.Builder(AddComplaint.this)
                                .setTitle("Success")
                                .setMessage(response.body().getMessage()+".\nAdmin will response you back via email or your contact number.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).show();
                    } else {
                        Toast.makeText(AddComplaint.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AddComplaint.this, "An error occured, please try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error: ", t.getLocalizedMessage().toString());
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
                status_msg_img.setText("Image Selected");
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