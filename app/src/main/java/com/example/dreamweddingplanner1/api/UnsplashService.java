package com.example.dreamweddingplanner1.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface UnsplashService {
    @Headers("Accept-Version: v1")
    @GET("search/photos")
    Call<UnsplashResponse> searchPhotos(
            @Query("query") String query,
            @Query("per_page") int perPage,
            @Query("client_id") String clientId
    );
}