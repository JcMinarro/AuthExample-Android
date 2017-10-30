package com.jcminarro.authexample.internal.network.refresh;

import com.jcminarro.authexample.internal.network.authorizator.UnauthorizatedApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

@UnauthorizatedApi
public interface RefreshEndpoint {

    @POST("/refresh")
    Call<RefreshResponse> refreshTokens(@Body RefreshBody refreshBody);
}
