package com.jcminarro.authexample.internal.network.login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginEndpoint {

    @POST("/login")
    Call<LoginResponse> login(@Body LoginBody loginBody);
}
