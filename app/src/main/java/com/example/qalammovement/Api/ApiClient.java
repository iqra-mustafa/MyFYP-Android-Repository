package com.example.qalammovement.Api;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    public static final String BASE_URL = Constant.BASE_URL;
  // public static final String BASE_URL = "http://192.168.1.169/fyp/public/api/";
    public static final String BASE_URL = "https://plutopakistan.com/fyp/public/api/";
    public static final String BASE_URL_IMAGE = "https://plutopakistan.com/fyp/public/assets/images/";
    public  static  final String BASE_URL_SCHOOL_IMAGES = "https://plutopakistan.com/fyp/public/schoolimages/";
    public  static  final String BASE_URL_GALLERY = "https://plutopakistan.com/fyp/public/gallery/";

    //public static final String BASE_URL_IMAGE = "http://192.168.18.206/QM/admin/public/assets/images/";
    public static Retrofit retrofit = null;

//set the timeout in milliseconds

    public static Retrofit getApiClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();


        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;

    }

}