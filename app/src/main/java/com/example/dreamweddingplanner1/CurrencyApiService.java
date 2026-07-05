package com.example.dreamweddingplanner1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CurrencyApiService {

    @GET("v6/{apiKey}/latest/LKR")
    Call<ExchangeRateResponse> getRates(
            @Path("apiKey") String apiKey
    );
}