package com.example.nasaapod.io;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.nasaapod.dto.Apod;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NasaService {

    private static NasaService INSTANCE;
    private final NasaApi nasaApi;

    public static NasaService getInstance() {
        if (INSTANCE == null) {

            INSTANCE = new NasaService("https://api.nasa.gov/");
        }
        return INSTANCE;
    }

    private NasaService(final String baseUrl) {


        OkHttpClient.Builder builder = new OkHttpClient.Builder();

//        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
////            builder.addNetworkInterceptor(httpLoggingInterceptor); //networkInterceptor won't show gzipped payload, but more accurate in timing.
//            builder.addInterceptor(httpLoggingInterceptor);
//        }

        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        String a = "123";
        String bb = this.create2(a);

        this.nasaApi = retrofit.create(NasaApi.class);

    }

    public Observable<Apod> getApod(String date) {
        return this.nasaApi.retrieveApod(date);
    }

    public Observable<Bitmap> getImage(String imagePath) {
        return this.nasaApi.retrieveImage(imagePath).map( responseBody -> BitmapFactory.decodeStream(responseBody.byteStream()));
    }


    public <T> T create2(Object obj) {
        return (T) obj;
    }
}
