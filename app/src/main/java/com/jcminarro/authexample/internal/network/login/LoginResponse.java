package com.jcminarro.authexample.internal.network.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private final @SerializedName("accessToken") String accessToken;
    private final @SerializedName("refreshToken") String refreshToken;

    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
