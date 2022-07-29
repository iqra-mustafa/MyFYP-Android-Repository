package com.example.qalammovement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.qalammovement.Api.ApiClient;
import com.example.qalammovement.Fragments.Gallary_Fragment;
import com.example.qalammovement.Model.GalleryImages;
import com.example.qalammovement.Model.ImageModel;
import com.example.qalammovement.R;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter  extends BaseAdapter {
    Context context;
    private List<GalleryImages> arraylist;
    public ImageAdapter(Context context, List<GalleryImages> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
    }
    @Override
    public int getCount() {
        return arraylist.size();
    }
    @Override
    public Object getItem(int position) {
        return arraylist.get(position);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView=(ImageView) convertView;
        if (imageView ==  null) {
           imageView=new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(350,400));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        Glide.with(context).load(ApiClient.BASE_URL_GALLERY+arraylist.get(position).getImage()).into(imageView);
        return imageView;
    }
}
