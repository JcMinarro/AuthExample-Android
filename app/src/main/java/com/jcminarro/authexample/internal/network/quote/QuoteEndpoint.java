package com.jcminarro.authexample.internal.network.quote;

import com.jcminarro.authexample.internal.network.authorizator.AuthorizatedApi;

import retrofit2.Call;
import retrofit2.http.GET;

@AuthorizatedApi
public interface QuoteEndpoint {

    @GET("/quote")
    Call<QuoteResponse> getRandomQuote();
}
