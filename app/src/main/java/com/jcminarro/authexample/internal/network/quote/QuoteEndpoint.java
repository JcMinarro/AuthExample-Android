package com.jcminarro.authexample.internal.network.quote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuoteEndpoint {

    @GET("/quote")
    Call<QuoteResponse> getRandomQuote();
}
