package com.jcminarro.authexample.internal.network.refresh;

import com.google.gson.annotations.SerializedName;

public class RefreshResponse {
    private final @SerializedName("accessToken") String accessToken;
    private final @SerializedName("refreshToken") String refreshToken;

    public RefreshResponse(String accessToken, String refreshToken) {
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
