package com.lateralx.civom.Network;

import com.lateralx.civom.Model.RetroPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    public static final String BASE_URL = "http://35.154.220.170:80";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public interface GetDataService {

        @GET("/api/assets")
        Call<List<RetroPhoto>> getAllPhotos();
    }

}
