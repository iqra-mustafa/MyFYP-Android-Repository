package com.example.qalammovement.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.qalammovement.Activity.Full_ScreenImage;
import com.example.qalammovement.Activity.SchoolListActivity;
import com.example.qalammovement.Adapters.ImageAdapter;
import com.example.qalammovement.Adapters.SchoolList;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Api.ApiInterface;
import com.example.qalammovement.Model.GalleryImages;
import com.example.qalammovement.Model.ImageModel;
import com.example.qalammovement.Model.School;
import com.example.qalammovement.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gallary_Fragment extends Fragment {
    ArrayList<Integer> image = new ArrayList<>(Arrays.asList(R.drawable.a,
            R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.pic2,
            R.drawable.pic3, R.drawable.school_1, R.drawable.school_2,
            R.drawable.school_3, R.drawable.school_4, R.drawable.school_5,
            R.drawable.school_6));

    List<GalleryImages> list;

    GridView gridView;
    View view;
    ProgressDialog progressDialog;
    ImageAdapter adpter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallary, container, false);
        progressDialog = new ProgressDialog(getActivity());
        gridView = (GridView) view.findViewById(R.id.gridview);
        getGalleryImages();
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View view, int position, long id) {
//                int pos_item = image.get(position);
//                showdialogbox((Integer) adpter.getItem(pos_item));
//            }
//        });
        return view;
    }

    public void showdialogbox(final int i) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.clicked_image);
        ImageView img = dialog.findViewById(R.id.image_fullscreen);
        AppCompatButton fullbtn = dialog.findViewById(R.id.Full_Button);
        AppCompatButton cancelbtn = dialog.findViewById(R.id.Cancel_Button);

        img.setImageResource(i);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        fullbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Full_ScreenImage.class);
                intent.putExtra("img_id", i);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void getGalleryImages(){
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<GalleryImages>> call = apiInterface.getGalleryImages();
        call.enqueue(new Callback<List<GalleryImages>>() {
            @Override
            public void onResponse(Call<List<GalleryImages>> call, Response<List<GalleryImages>> response) {
                progressDialog.hide();
                //Toast.makeText(StudentListActivity.this, "success", Toast.LENGTH_SHORT).show();
                list = response.body();
               adpter = new ImageAdapter(getActivity(), list);
                gridView.setAdapter(adpter);

            }

            @Override
            public void onFailure(Call<List<GalleryImages>> call, Throwable t) {
                progressDialog.hide();
                Log.d("error: ", t.getLocalizedMessage().toString());
            }
        });
    }
}


