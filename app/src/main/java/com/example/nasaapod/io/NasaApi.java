package com.example.nasaapod.io;

import com.example.nasaapod.dto.Apod;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NasaApi {

    @GET("planetary/apod?api_key=hiMmOG6IQDxkVjdzzdkE37Y0SDsKmG9P7ybF8i9L")
    Observable<Apod> retrieveApod(@Query("date") String date);

    @GET("https://apod.nasa.gov/{imagePath}")
    Observable<ResponseBody> retrieveImage(@Path(value = "imagePath", encoded=true) String imagePath);

}
