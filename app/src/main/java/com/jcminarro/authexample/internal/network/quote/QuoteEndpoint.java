package com.jcminarro.authexample.internal.network.quote;

import retrofit2.Call;
import retrofit2.http.POST;

public interface QuoteEndpoint {

    @POST("/quote")
    Call<QuoteResponse> getRandomQuote();
}
