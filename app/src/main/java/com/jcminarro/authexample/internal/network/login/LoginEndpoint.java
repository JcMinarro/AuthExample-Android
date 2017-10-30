package com.jcminarro.authexample.internal.network.login;

import com.jcminarro.authexample.internal.network.authorizator.UnauthorizatedApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

@UnauthorizatedApi
public interface LoginEndpoint {

    @POST("/login")
    Call<LoginResponse> login(@Body LoginBody loginBody);
}
