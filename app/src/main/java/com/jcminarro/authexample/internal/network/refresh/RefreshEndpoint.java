package com.jcminarro.authexample.internal.network.refresh;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RefreshEndpoint {

    @POST("/refresh")
    Call<RefreshResponse> refreshTokens(@Body RefreshBody refreshBody);
}
