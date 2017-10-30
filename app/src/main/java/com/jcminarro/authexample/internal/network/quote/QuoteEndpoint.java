package com.jcminarro.authexample.internal.network.quote;

import com.jcminarro.authexample.internal.network.authorizator.AuthorizatedApi;
import com.jcminarro.authexample.internal.network.reauthorizate.ReauthorizatedApi;

import retrofit2.Call;
import retrofit2.http.GET;

@AuthorizatedApi
@ReauthorizatedApi
public interface QuoteEndpoint {

    @GET("/quote")
    Call<QuoteResponse> getRandomQuote();
}
