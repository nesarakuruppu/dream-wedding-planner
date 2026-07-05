package com.example.dreamweddingplanner1.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyApiService {

    @GET("v4/latest/USD")
    Call<CurrencyResponse> getLatestRates();
}